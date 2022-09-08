package com.luoyang.aldldemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luoyang.note.NoteBean

/**
 * 笔记适配器
 *
 * @author luoyang
 * @date 2022/7/10
 */
class NoteAdapter(var mContext: Context) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var mDataList: List<NoteBean> = ArrayList<NoteBean>()
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mText: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteBean = mDataList[position]
        holder.mText.text = noteBean.number
        holder.mText.setOnClickListener {
            mOnItemClickListener?.onItemClick(it, noteBean)
        }
        holder.mText.setOnLongClickListener { onLongClick(it, noteBean) }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    fun setData(mDataList: List<NoteBean>) {
        this.mDataList = mDataList
    }


    private fun onLongClick(p0: View, noteBean: NoteBean): Boolean {
        mOnItemLongClickListener?.onItemLongClick(p0, noteBean)
        return true
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, noteBean: NoteBean)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, noteBean: NoteBean)
    }

}