package com.luoyang.providerdemo

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.luoyang.note.NoteBean

/**
 *
 *
 * @author luoyang
 * @date 2022/7/20
 */
class ResolverManager {

    private object Singleton {
        val instance = ResolverManager()
    }

    fun getInstance(resolver1: ContentResolver?): ResolverManager {
        resolver = resolver1
        return Singleton.instance
    }


    companion object {
        private var resolver: ContentResolver? = null
        private const val TAG: String = "ResolverManager"

        private const val TABLE: String = "BiJi_number"
        private const val NAME: String = "number"
        private const val AUTHORITY = "com.luoyang.androidfunDemo.provider.MyNoteProvider"
        private val ALL_URI: Uri = Uri.parse("content://$AUTHORITY/$TABLE")
        private val SINGLE_URI: Uri = Uri.parse("content://$AUTHORITY/$TABLE/")
        private const val ID: String = "id"
    }


    /**
     * 查询
     */
    fun query(): List<NoteBean> {
        Log.e(TAG, " query resolver $resolver")
        val cursor: Cursor? = resolver?.query(ALL_URI, null, null, null, null)
        if (cursor == null) Log.e(TAG, " query cursor为空")
        val list = ArrayList<NoteBean>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val indexName: Int = cursor.getColumnIndex(NAME)
                val number = cursor.getString(indexName)
                Log.e(TAG, "query indexName: $indexName, number: $number")

                val noteBean = NoteBean(-1, number)
                list.add(noteBean)
            }
        }
        Log.e(TAG, "query list: $list")
        return list
    }

    /**
     * 插入数据
     */
    fun insert(new: String?) {
        val values: ContentValues = ContentValues()
        values.put(NAME, new)
        if (resolver == null) println("resolver为空")
        Log.e(TAG, "insert new: $new")
        resolver?.insert(ALL_URI, values)
    }

    /**
     * 删除操作
     */
    fun delete(name: String?) {
        var select: String = "$NAME = $name"
        Log.e(TAG, "delete name: $name")
        //会删除全部数据
        var i = resolver?.delete(SINGLE_URI, select, null)
        Log.e(TAG, "delete i :$i")
    }

    /**
     * 更新操作
     */
    fun update(new: String, old: String?) {
        val values: ContentValues = ContentValues()
        values.put(NAME, new)
        var select: String = "$NAME = ?"
        Log.e(TAG, "update new: $new, old: $old")
        resolver?.update(ALL_URI, values, select, arrayOf(old))

    }

}