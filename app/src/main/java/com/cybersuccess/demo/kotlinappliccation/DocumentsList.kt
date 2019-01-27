package com.cybersuccess.demo.kotlinappliccation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_documents_list.*

class DocumentsList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documents_list)
        setSupportActionBar(toolbar)
    }
}
