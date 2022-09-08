package com.luoyang.providerdemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luoyang.aldldemo.NoteAdapter
import com.luoyang.aldldemo.NoteList
import com.luoyang.aldldemo.R
import com.luoyang.aldldemo.RxBus
import com.luoyang.note.NoteBean
import rx.functions.Action1
import rx.schedulers.Schedulers

/**
 * 笔记视图,组件化可被外部应用
 *
 * @author luoyang
 * @date 2022/7/9
 */
class ResolverFragment : Fragment() {

    /**
     * data
     */
    private lateinit var mManager: ResolverManager

    private lateinit var mData: MutableList<NoteBean>
    private lateinit var mAdapter: NoteAdapter

    /**
     * view
     */
    private lateinit var mRecycler: RecyclerView
    private lateinit var mAddButton: Button
    private lateinit var mEmpty: TextView


//    private var mRXSubscribe =
//        RxBus.getDefault().toObservable(NoteList::class.java).subscribeOn(Schedulers.io())
//            .subscribe(Action1<NoteList>() {
//                Log.d("AIDLNoteManager", "mRXSubscribe NoteList$it")
//                mData = it.list as MutableList<NoteBean>
//                updateData()
//            })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.note_fragment, null)
        initData()
        initView(view)
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateData() {
        if (mData.size > 0) {
            mEmpty.visibility = View.GONE
            mAdapter.setData(mData)
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        mData = mManager.query().toMutableList()
        updateData()
    }

    private fun initData() {
        mManager = ResolverManager().getInstance(activity?.contentResolver)
        mData = mManager.query().toMutableList()
    }

    private fun initView(view: View) {
        mEmpty = view.findViewById(R.id.tv_empty) as TextView

        mRecycler = view.findViewById(R.id.note_content) as RecyclerView
        mRecycler.layoutManager = LinearLayoutManager(context)

        mAdapter = NoteAdapter(requireContext())
        mRecycler.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {
            override fun onItemClick(view: View, noteBean: NoteBean) {
                showUpdateDialog(noteBean)
            }
        })
        mAdapter.setOnItemLongClickListener(object : NoteAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, noteBean: NoteBean) {
                showDeleteDialogById(noteBean)
            }

        })

        mAddButton = view.findViewById(R.id.add_note) as Button
        mAddButton.setOnClickListener {
            addNote()
        }
        updateData()
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
                mManager.delete(noteBean.number)
                mData.remove(noteBean)
                updateData()
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
                mManager.update(editText.text.toString(), noteBean.number)
                noteBean.number = editText.text.toString()
                updateData()
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
                mManager.insert(noteBean.number)
                mData.add(0, noteBean)
                updateData()
            }.setNegativeButton("取消", null).show()
    }

    override fun onDestroy() {
        super.onDestroy()

//        if (!mRXSubscribe.isUnsubscribed) {
//            mRXSubscribe.unsubscribe()
//        }
    }

}

