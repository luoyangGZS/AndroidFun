package com.luoyang.androidfunDemo.retrofit.show

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import com.luoyang.androidfunDemo.R
import com.luoyang.androidfunDemo.bean.ItemBean
import com.luoyang.androidfunDemo.retrofit.show.SearchBar.CallBack
import com.luoyang.androidfunDemo.util.JsonUtils
import com.luoyang.androidfunDemo.util.SearchParse
import com.luoyang.androidfunDemo.util.ThreadPoolUtil
import com.luoyang.androidfunDemo.util.Utils
import com.luoyang.base.base.BaseActivity

class HttpURLActivity : BaseActivity(), CallBack {

    private lateinit var mSearchContainer: RelativeLayout
    private lateinit var mSearchBar: SearchBar
    private var mSuggestListView: ListView? = null
    private var mSuggestView: LinearLayout? = null
    private var mSuggestAdapter: SearchSuggestAdapter? = null
    private var mKeyWord = ""
    private val mSuggestBeans: MutableList<ItemBean> = ArrayList<ItemBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
        initView()
    }

    private fun initView() {
        mSearchContainer = findViewById(R.id.search_container)
        mSearchBar = findViewById(R.id.search_bar)
        mSearchBar.setCallBack(this)

    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            search(mKeyWord)
        } else if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            try {
                mKeyWord = mSearchBar.editStr
                if (TextUtils.isEmpty(mKeyWord)) {
                    mKeyWord = mSearchBar.editHint
                }
                search(mKeyWord)
            } catch (e: Exception) {
//                e.printStackTrace();
            }
        }
        return false
    }

    override fun onClear() {
    }

    override fun onClickSearch(keyWord: String) {
        search(keyWord)
    }

    override fun onBack() {
        back()
    }

    public fun search(sug: String) {
        mSearchBar.hideSoftInput()
        if (!TextUtils.isEmpty(sug)) {
            mKeyWord = sug
            mSearchBar.setEditext(sug)
            setSearchStatus()
        }
    }

    private fun setSearchStatus() {
        if (mSuggestListView == null) {
            mSuggestView = Utils.inflate(baseContext, R.layout.list_view_suggest) as LinearLayout
            mSuggestListView = mSuggestView!!.findViewById<ListView>(R.id.list_view)
            mSuggestListView?.setBackgroundResource(R.color.white)
        }
        mSearchContainer.removeAllViews()
        if (mSuggestAdapter != null) {
            mSuggestBeans.clear()
            mSuggestAdapter?.notifyDataSetChanged()
        }
        mSearchContainer.addView(
            mSuggestView, RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
        )
        requestSearchSuggest(mKeyWord)

    }

    /**
     * HttpURLConnection请求方式
     */
    private fun requestSearchSuggest(query: String) {
        ThreadPoolUtil.post {
            val suggestJson: String =
                JsonUtils.doGet(SearchParse.composeSuggestURL(query))
            if (suggestJson != JsonUtils.CONNECT_FAIL) {
                val beans: List<ItemBean>? = SearchParse.parseSearchSuggest(suggestJson)
                rendSearchingUi(beans)
            }
        }
    }
    private val mHandler = Handler()
    private fun rendSearchingUi(beans: List<ItemBean>?) {
        mHandler.post(Runnable {
            if (mSuggestAdapter == null) {
                if (beans != null) {
                    mSuggestBeans.addAll(beans)
                }
                mSuggestAdapter = SearchSuggestAdapter(this, mSuggestBeans)
                if (mSuggestListView != null) {
                    mSuggestListView!!.adapter = mSuggestAdapter
                }
            } else {
                mSuggestBeans.clear()
                if (beans != null) {
                    mSuggestBeans.addAll(beans)
                }
                mSuggestAdapter?.notifyDataSetChanged()
            }
            if (mSuggestListView != null) {
                mSuggestListView!!.visibility = View.VISIBLE
            }
        })
    }

}