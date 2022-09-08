   package com.luoyang.providerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luoyang.aldldemo.NoteFragment
import com.luoyang.aldldemo.R

   class ResolverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ractivity)

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.note_resolver, ResolverFragment(), "ResolverFragment")
        transaction.addToBackStack("")
        transaction.commit()
    }
}