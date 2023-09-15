package com.example.database

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private  lateinit var button1 :Button
    private  lateinit var countText : TextView
    private  lateinit var  context : Context
    private lateinit var count: TextView
    private  lateinit var  listview1 : ListView
    private  lateinit var dbHandler:DbHandler
    private  lateinit var  todosDisplayMain:List<ToDo>
    // first execute onCreate function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get the all id to relevant to the this activity
        count = findViewById(R.id.todocount)
        button1 = findViewById(R.id.add)
        countText = findViewById(R.id.todocount)
        listview1 = findViewById(R.id.todolist)
        context = this
        //dbhandler class calling
        dbHandler = DbHandler(context)
        //initialize the list
        todosDisplayMain = mutableListOf<ToDo>()
        //get the content
        todosDisplayMain = dbHandler.getAllToDos()
        // pass to the adapter to display the content
        val adapter = TodoList(this,R.layout.single_text_view,todosDisplayMain)
        listview1.adapter=adapter
        // display the count
        val countTodo = dbHandler.countToDo()
        count.text = "You have $countTodo todos"
        //move the activity one from to another using intent
        button1.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,AddToDo::class.java)
            startActivity(intent)
        })

        listview1.setOnItemClickListener { parent, view, position, id ->
            val todo = todosDisplayMain[position]
            val builder = AlertDialog.Builder(context)
            builder.setTitle(todo.title)
            builder.setMessage(todo.description)
            builder.setPositiveButton("Finished") { dialog, which ->
                todo.finished = System.currentTimeMillis()
                dbHandler.updateSingleToDo(todo)
                startActivity(Intent(context, MainActivity::class.java))
            }
            builder.setNegativeButton("Delete") { dialog, which ->
                dbHandler.deleteToDo(todo.id)
                startActivity(Intent(context, MainActivity::class.java))
            }
            builder.setNeutralButton("Update") { dialog, which ->
                val intent = Intent(context, EditTodoAll::class.java)
                intent.putExtra("id", todo.id.toString())
                startActivity(intent)
            }
            builder.show()
        }



    }
}
