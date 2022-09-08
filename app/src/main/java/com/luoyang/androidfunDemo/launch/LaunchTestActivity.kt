package com.luoyang.androidfunDemo.launch

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.luoyang.androidfunDemo.R
import com.luoyang.androidfunDemo.livedata.TestUserBean
import com.luoyang.androidfunDemo.livedata.UserViewModel
import com.luoyang.androidfunDemo.singleton.SingletonSync
import com.luoyang.base.base.BaseActivity
import kotlinx.coroutines.*
import java.util.*

class LaunchTestActivity : BaseActivity() {

    private lateinit var mLaunchText: TextView

    companion object {
        const val TAG = "LaunchTestActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_test)
        initView()

        SingletonSync.getInstance(this);
//        testBlockLaunch()
        testGlobalLaunch()
        Log.d(TAG, "testLet ${testLet()}")
        Log.d(TAG, "testWith ${testWith()}")
        Log.d(TAG, "testApply ${testApply()}")
        Log.d(TAG, "testRun ${testRun()}")
        lifecycle.addObserver(MyLifecycleObserver())
        initLiveData()
    }

    private fun initView() {
        mLaunchText = findViewById(R.id.launch_text)
    }

    /**
     * 改变隔壁界面，livedataActivity的数据
     */
    private fun initLiveData() {
        val listData: MutableLiveData<TestUserBean> = UserViewModel.mUserMutableLiveData
        listData.value = TestUserBean("张三", 22, 10f)
    }

    fun testLet(): Int {
        "testLet".let {
            Log.d(TAG, "testLet 1it $it")
            Log.d(TAG, "testLet 2it $it")
            Log.d(TAG, "testLet 3it $it")
            Log.d(TAG, "testLet 4it $it")
            Log.d(TAG, "testLet 5it $it")
            return 1
        }
    }

    fun testWith(): ArrayList<String> {
        return with(ArrayList<String>()) {
            add("testWith")
            add("testWith")
            add("testWith")
            this
        }
    }

    fun testApply(): ArrayList<String> {
        return ArrayList<String>().apply {
            add("apply1")
            add("apply2")
            add("apply3")
        }
    }

    fun testRun() {
        return "testRun".run {
            Log.d(TAG, "testRun this $this")
            Date()
            Log.d(TAG, "testRun")
        }.let { Log.d(TAG, "testRun it11 $it") }
    }


    /**
     * 立即创建activity
     * 立即执行，声明为main后，可以全局可以更新UI
     * 执行结果：分三次更新UI
     *
     * 最终打印结果
     * Global go go
     * GlobalLaunch1 started
     * GlobalLaunch2 started
     * GlobalLaunch2 ended
     * GlobalLaunch1 ended
     */
    private fun testGlobalLaunch() {
        val buffer = StringBuffer()
        GlobalScope.launch(Dispatchers.Main) {//全局作用域用于启动在整个应用程序生命周期内运行且不会过早取消的顶级协程

            launch() {//lauch也是协程构建器，启动了一个新协程,我们称它为coroutine#1， 它是coroutine#main的子协程
//            launch(Dispatchers.IO) {//lauch也是协程构建器，启动了一个新协程,我们称它为coroutine#1， 它是coroutine#main的子协程
                buffer.append("GlobalLaunch1 started \n")
                Log.d(TAG, "GlobalLaunch1 started")//打印信息
                delay(3000)//暂停3000ms，也说是挂起
                buffer.append("GlobalLaunch1 ended \n")
                Log.d(TAG, "GlobalLaunch1 ended")//打印信息
                mLaunchText.text = buffer.toString()
            }

//            launch() {//同上，只是顺序在coroutine#1的后面 我们称其coroutine#2
            launch() {//同上，只是顺序在coroutine#1的后面 我们称其coroutine#2
                buffer.append("GlobalLaunch2 started \n")
                Log.d(TAG, "GlobalLaunch2 started")//打印信息
                delay(2000)//暂停2000ms，也说是挂起
                buffer.append("GlobalLaunch2 ended \n")
                Log.d(TAG, "GlobalLaunch2 ended")//打印信息
                mLaunchText.text = buffer.toString()
            }
            buffer.append("Global go go \n")
            Log.d(TAG, "Global go go")//打印信息
            mLaunchText.text = buffer.toString()
        }
    }


    /**
     * 延迟启动activity,默认为mian线程
     *
     * 执行结果
     * D/LaunchTestActivity: Block go go
     * D/LaunchTestActivity: BlockLaunch1 started
     * D/LaunchTestActivity: BlockLaunch2 started
     * D/LaunchTestActivity: BlockLaunch2 ended
     * D/LaunchTestActivity: BlockLaunch1 ended
     *
     *
     *
     *
     *所有的行都加了注释，那么我们看到一共有3个协程 #main, #1, #2。
     * 那么显然#main是最先执行的协程, 它主体上干了3件事，
     * 第一件事 创建#1,然后创建#2,最后打印 "Block go go",
     * 你可能会说:"那为啥先输出gogo 而不是”BlockLaunch1 started"? 第一件事不是创建#1吗，
     * 是的第一件事是创建#1,
     * 但协程是需要唤醒才能执行的，
     * 创建#1时，主协程#main还没有到达挂起点，
     * #1只能是挂起（suspend）状态，它在等待调度或者说在等待挂起点。那么#2也是一样的道理，
     * 这样的话#main最后会执行"Block go go"，也就是我们先看到"go go"输出的原因了，
     * 此时主协程没事情做了，这样就自然到达了它的挂起点，注意#main此时并没有退出只是挂起了，
     * 这时根据先后顺序协程#1发现了这个挂起点，从而得到了调度唤醒，
     * 它首先执行了 "BlockLaunch1 started", 这显而易见。然后执行“delay(3000)”,
     * 这个delay就是挂起的意思，此时#1挂起，
     * #2得到唤醒 执行"BlockLaunch1 started"，
     * 然后#2也挂起delay（2000）, 那之后其实是显而易见的
     * #2挂起的时间短 所以“BlockLaunch2 ended”得以先输出，#2完成使命彻底消亡，
     * 最后是“BlockLaunch1 ended”，#1消亡，
     * 此时#main发现所有子协程已经 退出，
     * 它也功成身退，彻底退出，至此程序得以结束。
     */
    private fun testBlockLaunch() {
        val buffer = StringBuffer()
        runBlocking() {//runBlocking是一个协程构建器，它会启动一个阻塞线程的协程，但也是协程，coroutine#main
            launch() {//lauch也是协程构建器，启动了一个新协程,我们称它为coroutine#1， 它是coroutine#main的子协程
//            launch(Dispatchers.IO) {//lauch也是协程构建器，启动了一个新协程,我们称它为coroutine#1， 它是coroutine#main的子协程
                buffer.append("BlockLaunch1 started \n")
                Log.d(TAG, "BlockLaunch1 started")//打印信息
                delay(3000)//暂停3000ms，也说是挂起
                buffer.append("BlockLaunch1 ended \n")
                Log.d(TAG, "BlockLaunch1 ended")//打印信息
            }

            launch {//同上，只是顺序在coroutine#1的后面 我们称其coroutine#2
                buffer.append("BlockLaunch2 started \n")
                Log.d(TAG, "BlockLaunch2 started")//打印信息
                delay(2000)//暂停2000ms，也说是挂起
                buffer.append("BlockLaunch2 ended \n")
                Log.d(TAG, "BlockLaunch2 ended")//打印信息
            }
            buffer.append("Block go go \n")
            Log.d(TAG, "Block go go")//打印信息
        }
        mLaunchText.text = buffer.toString()
    }

}