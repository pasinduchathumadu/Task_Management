package com.example.database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.widget.ImageView
import android.widget.TextView
import java.util.Locale


class TodoList(context: Context, resource: Int, todos: List<ToDo>) :
    ArrayAdapter<ToDo>(context, resource, todos) {

    private val context: Context = context
    private val resource: Int = resource
    private val todos: List<ToDo> = todos
    private var filteredTodos: List<ToDo> = todos

    override fun getCount(): Int {
        return filteredTodos.size
    }

    override fun getItem(position: Int): ToDo? {
        return filteredTodos[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(resource, parent, false)

        val title = row.findViewById<TextView>(R.id.title)
        val description = row.findViewById<TextView>(R.id.description)
        val imageView = row.findViewById<ImageView>(R.id.onGoing)

//        val toDo = todos[position]
//        title.text = toDo.title
//        description.text = toDo.description
//        imageView.visibility = View.INVISIBLE
//
//        if (toDo.finished > 0) {
//            imageView.visibility = View.VISIBLE
//        }
//
//
//        return row
//    }
        val toDo = filteredTodos[position]
        title.text = toDo.title
        description.text = toDo.description
        imageView.visibility = View.INVISIBLE

        if (toDo.finished > 0) {
            imageView.visibility = View.VISIBLE
        }

        return row
    }

    // Filter the list based on the search query
    fun filter(query: CharSequence?) {
        val filteredList = mutableListOf<ToDo>()

        if (!query.isNullOrEmpty()) {
            val searchQuery = query.toString().toLowerCase(Locale.getDefault())
            for (todo in todos) {
                if (todo.title?.toLowerCase(Locale.getDefault())?.contains(searchQuery) == true ||
                    todo.description?.toLowerCase(Locale.getDefault())?.contains(searchQuery) == true) {
                    filteredList.add(todo)
                }
            }
        } else {
            filteredList.addAll(todos)
        }

        filteredTodos = filteredList
        notifyDataSetChanged()
    }

}
