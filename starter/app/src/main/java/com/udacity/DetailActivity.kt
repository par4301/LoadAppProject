package com.udacity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {
    private var fileName = ""
    private var status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        ok_button.setOnClickListener {
            returnToMainActivity()
        }

        fileName = intent.getStringExtra("fileName").toString()
        status = intent.getStringExtra("status").toString()
        tv_fileName.text = fileName
        tv_status.text = status
    }

    fun returnToMainActivity() {
        val  mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
    }
}
