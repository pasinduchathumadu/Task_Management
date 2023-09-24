package com.example.database

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.util.Date


class AddToDo : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var desc: EditText
    private lateinit var date: EditText
    private lateinit var priority :CheckBox
    private lateinit var add: Button
    private lateinit var dbHandler: DbHandler
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        title = findViewById(R.id.editToDoTextTitle)
        desc = findViewById(R.id.editToDoTextDescription)
        date = findViewById(R.id.editToDoDate)
        priority = findViewById(R.id.editToDoPriority)
        add = findViewById(R.id.buttonEdit)
        context = this

        dbHandler = DbHandler(context)

        add.setOnClickListener(View.OnClickListener {
            val userTitle = title.text?.toString()
            val userDesc = desc.text?.toString()
            val userDate = date.text?.toString()
            val started = System.currentTimeMillis()
            if(userTitle!="" && userDesc!="" && userDate!=""){
                if(priority.isChecked) {
                    val toDo = ToDo(userTitle, userDesc, userDate, 1, started, 0)
                    dbHandler.addToDo(toDo)
                    startActivity(Intent(context, MainActivity::class.java))
                }else{
                    val toDo = ToDo(userTitle, userDesc, userDate, 0, started, 0)
                    dbHandler.addToDo(toDo)
                    startActivity(Intent(context, MainActivity::class.java))
                }
            }
            startActivity(Intent(context, MainActivity::class.java))
        })
    }
}
