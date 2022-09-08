package com.luoyang.note

import android.os.Bundle
import com.luoyang.base.base.BaseActivity


/**
 * 洛阳笔记
 */
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.note_content, NoteFragment(), "NoteFragment")
        transaction.addToBackStack("")
        transaction.commit()

//        val intentStart = Intent(this@MainActivity, AIDLService::class.java)
//        startService(intentStart)
    }
}