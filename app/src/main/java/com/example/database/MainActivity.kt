package com.example.database

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private  lateinit var button1 :Button
    private  lateinit var countText : TextView
    private  lateinit var  context : Context
    private lateinit var count: TextView
    private  lateinit var  listview1 : ListView
    private  lateinit var dbHandler:DbHandler
    private  lateinit var  todosDisplayMain:List<ToDo>
    private lateinit var searchView: EditText
    private lateinit var buttonsearch:Button
    private lateinit var  buttonall :Button
    private lateinit var  buttonpersonal : Button
    private lateinit var buttonaccedemic: Button
    private lateinit var buttonother : Button






    // first execute onCreate function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get the all id to relevant to the this activity
        count = findViewById(R.id.todocount)
        button1 = findViewById(R.id.add)
        countText = findViewById(R.id.todocount)
        listview1 = findViewById(R.id.todolist)
        buttonsearch = findViewById(R.id.searchButton)
        searchView = findViewById(R.id.searchView)
        buttonall = findViewById(R.id.buttonall)
        buttonaccedemic = findViewById(R.id.buttonacedemic)
        buttonpersonal = findViewById(R.id.buttonpersonal)
        buttonother = findViewById(R.id.buttonother)

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
        buttonall.setOnClickListener(View.OnClickListener {
            todosDisplayMain = dbHandler.getAllToDos()
            // pass to the adapter to display the content
            val adapter = TodoList(this,R.layout.single_text_view,todosDisplayMain)
            listview1.adapter=adapter
        })
        buttonaccedemic.setOnClickListener(View.OnClickListener {
            todosDisplayMain = dbHandler.getAccedemicToDos()
            // pass to the adapter to display the content
            val adapter = TodoList(this,R.layout.single_text_view,todosDisplayMain)
            listview1.adapter=adapter

        })
        buttonpersonal.setOnClickListener(View.OnClickListener {
            todosDisplayMain = dbHandler.getPersonalToDos()
            // pass to the adapter to display the content
            val adapter = TodoList(this,R.layout.single_text_view,todosDisplayMain)
            listview1.adapter=adapter
        })
        buttonother.setOnClickListener(View.OnClickListener {
            todosDisplayMain = dbHandler.getOtherToDos()
            // pass to the adapter to display the content
            val adapter = TodoList(this,R.layout.single_text_view,todosDisplayMain)
            listview1.adapter=adapter

        })
        buttonsearch.setOnClickListener(View.OnClickListener {
            val input = searchView.text?.toString()
            val result = input?.let { it1 -> dbHandler.search(it1) }
            val adapter = result?.let { it1 -> TodoList(this,R.layout.single_text_view, it1) }
            listview1.adapter=adapter
        })
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
//
//    private fun filterList(query: String?) {
//
//        if (query != null) {
//            val filteredList = ArrayList<LanguageData>()
//            for (i in todosDisplayMain) {
//                if (i.title.lowercase(Locale.ROOT).contains(query)) {
//                    filteredList.add(i)
//                }
//            }
//
//            if (filteredList.isEmpty()) {
//                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
//            } else {
//                adapter.setFilteredList(filteredList)
//            }
//        }
//    }
}