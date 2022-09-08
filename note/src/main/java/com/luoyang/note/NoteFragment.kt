package com.luoyang.note

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luoyang.base.base.BaseFragment
import com.luoyang.note.NoteAdapter.OnItemClickListener

/**
 * 笔记视图,组件化可被外部应用
 *
 * @author luoyang
 * @date 2022/7/9
 */
class NoteFragment : BaseFragment() {

    /**
     * data
     */
    private lateinit var mDao: NoteNumberDAO
    private lateinit var mData: MutableList<NoteBean>
    private lateinit var mAdapter: NoteAdapter

    /**
     * view
     */
    private lateinit var mRecycler: RecyclerView
    private lateinit var mAddButton: Button
    private lateinit var mEmpty: TextView

    override fun loadData() {

    }

    override fun initView() {
        mDao = NoteNumberDAO.getInstance(context)
        mData = mDao.queryAll()

        mEmpty = findView(R.id.tv_empty) as TextView
        if (mData.size > 0) {
            mEmpty.visibility = View.GONE
        }

        mRecycler = findView(R.id.rv_note_content) as RecyclerView
        mRecycler.layoutManager = LinearLayoutManager(context)

        mAdapter = NoteAdapter(mActivity)
        mAdapter.setData(mData)
        mRecycler.adapter = mAdapter
        mRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, noteBean: NoteBean) {
                showUpdateDialog(noteBean)
            }
        })
        mAdapter.setOnItemLongClickListener(object : NoteAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, noteBean: NoteBean) {
                showDeleteDialogById(noteBean)
            }

        })

        mAddButton = findView(R.id.add_note) as Button
        mAddButton.setOnClickListener {
            addNote()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        mData = mDao.queryAll()
        mAdapter.setData(mData)
        mAdapter.notifyDataSetChanged()
        if (mData.size > 0) {
            mEmpty.visibility = View.GONE
        }
        Log.d("lxj", "onResume");

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showDeleteDialogById(noteBean: NoteBean) {
        val textView = TextView(context)
        textView.text = noteBean.number
        AlertDialog.Builder(context)
            .setTitle("确定要删除以下记录吗，洛阳哥？")
            .setView(textView)
            .setNegativeButton("取消", null)
            .setPositiveButton("删除") { _, _ ->
                mDao.deleteById(noteBean.id)
                mData.remove(noteBean)
                mAdapter.notifyDataSetChanged()
                if (mData.size > 0) {
                    mEmpty.visibility = View.GONE
                }else{
                    mEmpty.visibility = View.VISIBLE
                }
            }.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showUpdateDialog(noteBean: NoteBean) {
        val editText = EditText(context)
        editText.setText(noteBean.number)
        AlertDialog.Builder(context)
            .setTitle("更新笔记")
            .setView(editText)
            .setPositiveButton("更新") { _, _ ->
                noteBean.number = editText.text.toString()
                mDao.update(noteBean)
                mAdapter.notifyDataSetChanged()
                if (mData.size > 0) {
                    mEmpty.visibility = View.GONE
                }
            }.setNegativeButton("取消", null).show()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun addNote() {
        val editText = EditText(context)
        editText.hint = "笔记输入"
        AlertDialog.Builder(context)
            .setTitle("添加笔记")
            .setView(editText)
            .setPositiveButton("添加") { _, _ ->
                val noteBean = NoteBean(-1, editText.text.toString())
                mDao.add(noteBean)
                mData.add(0, noteBean)
                mAdapter.notifyDataSetChanged()
                if (mData.size > 0) {
                    mEmpty.visibility = View.GONE
                }
            }.setNegativeButton("取消", null).show()
    }

    override fun provideContentViewId(): Int {
        return R.layout.note_fragment
    }


}