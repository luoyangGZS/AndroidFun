package com.luoyang.aldldemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    /**
     * data
     */
    private lateinit var mManager: AIDLNoteManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mManager = AIDLNoteManager.getInstance(this)
        mManager.bindCloneRemoteService()

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.note_content, NoteFragment(), "NoteFragment")
        transaction.addToBackStack("")
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        mManager.unbindCloneRemoteService()
    }

}

