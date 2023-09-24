package com.example.database

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText


class AddToDo : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var desc: EditText
    private lateinit var add: Button
    private lateinit var dbHandler: DbHandler
    private lateinit var context: Context
    private lateinit var button1 : Button
    private lateinit var button2 : Button
    private lateinit var button3 : Button
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        title = findViewById(R.id.editToDoTextTitle)
        desc = findViewById(R.id.editToDoTextDescription)
        add = findViewById(R.id.buttonEdit)
        button1 = findViewById(R.id.radioButton)
        button2 = findViewById(R.id.radioButton2)
        button3 = findViewById(R.id.radioButton3)
        context = this

        dbHandler = DbHandler(context)

        button1.setOnClickListener(View.OnClickListener {
            category = "Academic"
        })
        button2.setOnClickListener(View.OnClickListener {
            category = "Personal"
        })
        button3.setOnClickListener(View.OnClickListener {
            category = "Other"
        })

        add.setOnClickListener(View.OnClickListener {
            val userTitle = title.text?.toString()
            val userDesc = desc.text?.toString()
            val started = System.currentTimeMillis()

            if(userTitle!="" && userDesc!=""){
                val toDo = ToDo(userTitle, userDesc, started, 0,category)
                dbHandler.addToDo(toDo)
                startActivity(Intent(context, MainActivity::class.java))
            }
            startActivity(Intent(context, MainActivity::class.java))
        })
    }
}