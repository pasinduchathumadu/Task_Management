package com.example.database

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditTodoAll : AppCompatActivity() {
    private lateinit var title: EditText
    private lateinit var des: EditText
    private lateinit var edit: Button
    private lateinit var dbHandler: DbHandler
    private lateinit var context: Context
    private var updateDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo)

        context = this
        dbHandler = DbHandler(context)

        title = findViewById(R.id.editToDoTextTitle)
        des = findViewById(R.id.editToDoTextDescription)
        edit = findViewById(R.id.buttonEdit)

        val id = intent.getStringExtra("id")
        val todo = id?.let { dbHandler.getSingleToDo(it.toInt()) }

        if (todo != null) {
            title.setText(todo.title)
            des.setText(todo.description)
        }

        edit.setOnClickListener {
            val titleText = title.text?.toString()
            val descText = des.text?.toString()
            updateDate = System.currentTimeMillis()

            val toDo = id?.let { it1 -> ToDo(it1.toInt(), titleText, descText, 0, updateDate, null) }
            val state = toDo?.let { it1 -> dbHandler.updateSingleToDo(it1) }
            println(state)
            startActivity(Intent(context, MainActivity::class.java))
        }
    }
}
