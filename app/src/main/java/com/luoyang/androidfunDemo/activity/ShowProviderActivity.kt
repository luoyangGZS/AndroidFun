package com.luoyang.androidfunDemo.activity

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.TextView
import com.luoyang.androidfunDemo.R
import com.luoyang.base.base.BaseActivity
import com.luoyang.note.DBHelper

/**
 * content provider 调用展示
 */
class ShowProviderActivity : BaseActivity() {
    companion object {
        private var resolver: ContentResolver? = null
        private const val TABLE: String = DBHelper.DB_TABLE
        private const val TAG: String = "ShowProviderActivity"
        private const val NAME: String = "number"
        private const val AUTHORITY = "com.luoyang.androidfunDemo.provider.MyNoteProvider"
        private val ALL_URI: Uri = Uri.parse("content://$AUTHORITY/$TABLE")
        private val SINGLE_URI: Uri = Uri.parse("content://$AUTHORITY/$TABLE/")
        private const val ID: String = "id"
        private const val MESSAGE = 999
    }

    private lateinit var mThread: Thread
    private lateinit var text: TextView

    val mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MESSAGE -> {
                    resolver = contentResolver
                    val query = query()
                    text.text = query
                    text.setTextColor(resources.getColor(R.color.base_tab_selected_color))
                    Log.d(TAG, "handler massage 999")
                }
                else -> {
                    Log.d(TAG, "handler else")
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_provider)
        text = findViewById<TextView>(R.id.show_resolver)
        mHandler.sendEmptyMessage(MESSAGE)
        Log.d(TAG, "send massage 999")
//        mThread = Thread() {
//            resolver = contentResolver
//
////            insert()
////            update()
//            val query = query()
//            text.text = query
//            text.setTextColor(resources.getColor(R.color.base_tab_selected_color))
//        }
//        mThread.start();

//        ThreadPoolUtil.post {
//            resolver = contentResolver
//
//            val query = query()
//            text.text = query
//            text.setTextColor(resources.getColor(R.color.base_tab_selected_color))
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        mThread.isInterrupted
        mHandler.removeCallbacksAndMessages(null)
        Log.d(TAG, "removeCallbacksAndMessages")
    }

    /**
     * 查询
     */
    private fun query(): String {
        val buffer = StringBuffer()
        val cursor: Cursor? = resolver?.query(ALL_URI, null, null, null, null)
        if (cursor == null) {
            val empty = " query cursor为空"
            buffer.append(empty)
            Log.e(TAG, empty)
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val indexName: Int = cursor.getColumnIndex(NAME)
                val number = cursor.getString(indexName)
                val empty = " indexName: $indexName, number: $number \n  "
                buffer.append(empty)
                Log.e(TAG, empty)
            }
        }
        return buffer.toString()
    }

    /**
     * 插入数据
     */
    private fun insert() {
        val values: ContentValues = ContentValues()
        values.put(NAME, "旧名")
        if (resolver == null) println("resolver为空")
        resolver?.insert(ALL_URI, values)
    }

    /**
     * 删除操作
     */
    private fun delete(name: String) {
        var i = resolver?.delete(SINGLE_URI, "$ID=0", null)
        Log.e(TAG, "delete:$i")
    }

    /**
     * 更新操作
     */
    private fun update() {
        val values: ContentValues = ContentValues()
        values.put(NAME, "新名")
        var select: String = "$NAME = ?"
        resolver?.update(ALL_URI, values, select, arrayOf("旧名"))
    }

}