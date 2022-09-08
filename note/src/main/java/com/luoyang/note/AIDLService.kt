package com.luoyang.note

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class AIDLService : Service() {
    companion object {
        private const val TAG = "AIDLService"
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind mAidlNoteChange: $mAidlNoteChange")
        return mAidlNoteChange
    }

    private val mAidlNoteChange = object : NoteChange.Stub() {
        private var mDao: NoteNumberDAO = NoteNumberDAO.getInstance(this@AIDLService)

        override fun add(noteBean: NoteBean?) {
            Log.d(TAG, "add noteBean: $noteBean")
            if (noteBean != null) {
                mDao.add(noteBean)
            }
        }

        override fun deleteById(noteBean: NoteBean?) {
            Log.d(TAG, "deleteById noteBean: $noteBean")
            if (noteBean != null) {
//                mDao.deleteById(noteBean.id)
                mDao.deleteByNumber(noteBean.number)
            }
        }


        override fun update(noteBean: NoteBean?) {
            Log.d(TAG, "update noteBean: $noteBean")
            if (noteBean != null) {
                mDao.update(noteBean)
            }
        }

        override fun queryAll(): List<NoteBean> {
            Log.d(TAG, "queryAll")
            return mDao.queryAll()
        }

    }
}