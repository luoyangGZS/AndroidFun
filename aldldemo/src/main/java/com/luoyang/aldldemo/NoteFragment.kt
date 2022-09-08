package com.luoyang.aldldemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
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
import com.luoyang.note.NoteBean
import com.luoyang.providerdemo.ResolverActivity
import rx.functions.Action1
import rx.schedulers.Schedulers
import java.lang.reflect.TypeVariable

/**
 * 笔记视图,组件化可被外部应用
 *
 * @author luoyang
 * @date 2022/7/9
 */
class NoteFragment : Fragment() {

    /**
     * data
     */
    private lateinit var mManager: AIDLNoteManager

    private lateinit var mData: MutableList<NoteBean>
    private lateinit var mAdapter: NoteAdapter

    /**
     * view
     */
    private lateinit var mRecycler: RecyclerView
    private lateinit var mAddButton: Button
    private lateinit var mEmpty: TextView


    private var mRXSubscribe =
        RxBus.getDefault().toObservable(NoteList::class.java).subscribeOn(Schedulers.io())
            .subscribe(Action1<NoteList>() {
                Log.d("AIDLNoteManager", "mRXSubscribe NoteList$it")
                mData = it.list as MutableList<NoteBean>
                updateData()
            })

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
        mData = mManager.queryAllRemote()
        updateData()
    }

    private fun initData() {
        mManager = AIDLNoteManager.getInstance(context)
        mData = mManager.queryAllRemote()
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

        //添加跳转
        var jumpButton = view.findViewById(R.id.jump_view) as Button
        jumpButton.visibility = View.VISIBLE
        jumpButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(context,ResolverActivity::class.java)
            activity?.startActivity(intent)

        })
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
                mManager.deleteById(noteBean)
                mData.remove(noteBean)
                mAdapter.notifyDataSetChanged()
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
                mManager.update(noteBean)
                mAdapter.notifyDataSetChanged()
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
                mManager.add(noteBean)
                mData.add(0, noteBean)
                mAdapter.notifyDataSetChanged()
            }.setNegativeButton("取消", null).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (!mRXSubscribe.isUnsubscribed) {
            mRXSubscribe.unsubscribe()
        }
    }

}

