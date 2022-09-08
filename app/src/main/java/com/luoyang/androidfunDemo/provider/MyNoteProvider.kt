package com.luoyang.androidfunDemo.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.luoyang.note.DBHelper
import java.lang.IllegalArgumentException

class MyNoteProvider : ContentProvider() {


    companion object {
        private const val AUTHORITY = "com.luoyang.androidfunDemo.provider.MyNoteProvider"
        private const val TABLE: String = DBHelper.DB_TABLE
        private const val NOTE_CODE: Int = 1
        private const val NOTE_CODE_ITEM: Int = 2
        private const val PATH_NOTE_CODE: String = TABLE
        private const val PATH_NOTE_CODE_ITEM: String = "$TABLE/#"

        const val ID: String = "id"
    }

    private lateinit var helper: DBHelper
    private val matcher = UriMatcher(UriMatcher.NO_MATCH)


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = helper.writableDatabase
        var num = 0
        num = when (matcher.match(uri)) {
            NOTE_CODE -> db.delete(TABLE, null, null)
            NOTE_CODE_ITEM -> {
                val id = ContentUris.parseId(uri)
                val whereClause: String = "$ID=$id"
                db.delete(TABLE, whereClause, selectionArgs)
            }
            else -> throw IllegalArgumentException("delete unrecognized uri!")
        }
        return num
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = helper.writableDatabase
        when (matcher.match(uri)) {
            NOTE_CODE -> {
                val rowId: Long = db.insert(TABLE, null, values)
                if (rowId > 0) {
                    val uri1: Uri = ContentUris.withAppendedId(uri, rowId)
                    context?.contentResolver?.notifyChange(uri1, null)
                    return uri1

                }
            }
            else -> throw IllegalArgumentException("insert unrecognized uri!")
        }
        return null
    }

    override fun onCreate(): Boolean {
        helper = DBHelper(context)
        matcher.addURI(AUTHORITY, PATH_NOTE_CODE, NOTE_CODE)
        matcher.addURI(AUTHORITY, PATH_NOTE_CODE_ITEM, NOTE_CODE_ITEM)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = helper.writableDatabase
        return when (matcher.match(uri)) {
            NOTE_CODE ->
                db.query(TABLE, projection, selection, selectionArgs, null, null, null)


            NOTE_CODE_ITEM ->
                //                val id = ContentUris.parseId(uri)
                db.query(TABLE, null, "id=?", selectionArgs, null, null, null)

            else -> throw IllegalArgumentException("query unrecognized uri!")

        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val db = helper.writableDatabase
        var num = 0
        when (matcher.match(uri)) {
            NOTE_CODE -> num = db.update(TABLE, values, selection, selectionArgs)
            else -> throw IllegalArgumentException("update unrecognized uri!")
        }
        return num
    }
}