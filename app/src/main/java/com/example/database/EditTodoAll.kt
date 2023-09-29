package com.example.database

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import java.util.Calendar
import android.widget.ArrayAdapter
import android.view.View
import android.widget.AdapterView

class EditTodoAll : AppCompatActivity() {
    private lateinit var title: EditText
    private lateinit var des: EditText
    private lateinit var edit: Button
    private lateinit var dbHandler: DbHandler
    private lateinit var context: Context
    private var updateDate: Long = 0
    private lateinit var button1 : Button
    private lateinit var button2 : Button
    private lateinit var button3 : Button
    private lateinit var btnDatePicker : Button
    private lateinit var btnTimePicker : Button
    private lateinit var txtDate : EditText
    private lateinit var txtTime : EditText
    private lateinit var spinnerPriority : Spinner
    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0

    private var priority = ""
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo)

        context = this
        dbHandler = DbHandler(context)

        title = findViewById(R.id.editToDoTextTitle)
        des = findViewById(R.id.editToDoTextDescription)
        edit = findViewById(R.id.buttonEdit)
        button1 = findViewById(R.id.radioButton)
        button1 = findViewById(R.id.radioButton)
        button2 = findViewById(R.id.radioButton2)
        button3 = findViewById(R.id.radioButton3)
        btnDatePicker=findViewById(R.id.btn_date);
        btnTimePicker=findViewById(R.id.btn_time);
        txtDate=findViewById(R.id.in_date);
        txtTime=findViewById(R.id.in_time);
        spinnerPriority=findViewById(R.id.spinner_priority)

        val id = intent.getStringExtra("id")
        val todo = id?.let { dbHandler.getSingleToDo(it.toInt()) }

        if (todo != null) {
            title.setText(todo.title)
        }
        if (todo != null) {
            des.setText(todo.description)
            txtDate.setText(todo.date)
            txtTime.setText(todo.time)
//            priority.toString(todo.priority)

        }
        if(todo?.category =="Academic"){
           button1.isActivated=true
        }else if(todo?.category=="Personal"){
            button2.isActivated=true
        }else{
            button3.isActivated=true
        }

        val priorities: Array<String> = when (todo?.priority) {
            "High" -> arrayOf("High", "Medium", "Low")
            "Medium" -> arrayOf("Medium", "High", "Low")
            else -> arrayOf("Low", "Medium", "High")
        }
        // Create an ArrayAdapter to bind the data to the Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)

        // Set the dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Attach the adapter to the Spinner
        spinnerPriority.adapter = adapter

        spinnerPriority.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                // Handle the selected priority here
                priority = priorities[position]
                // You can do something with the selected priority
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Handle case where nothing is selected (if needed)
            }

        })

        btnDatePicker.setOnClickListener {
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    txtDate.setText("$dayOfMonth-${monthOfYear + 1}-$year")
                },
                mYear,
                mMonth,
                mDay
            )
            datePickerDialog.show()
        }

        btnTimePicker.setOnClickListener{
            val c = Calendar.getInstance()
            mHour = c.get(Calendar.HOUR_OF_DAY)
            mMinute = c.get(Calendar.MINUTE)

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { view: TimePicker?, hourOfDay: Int, minute: Int ->
                    txtTime.setText("$hourOfDay:$minute")
                },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }

        button1.setOnClickListener(View.OnClickListener {
            category = "Academic"
        })
        button2.setOnClickListener(View.OnClickListener {
            category = "Personal"
        })
        button3.setOnClickListener(View.OnClickListener {
            category = "Other"
        })



        edit.setOnClickListener {
            val titleText = title.text.toString()
            val decText = des.text.toString()
            updateDate = System.currentTimeMillis()
            val date = txtDate.text?.toString()
            val time = txtTime.text?.toString()
            val toDo = id?.let { it1 ->
                todo?.let { it2 ->
                    ToDo(it1.toInt(), titleText, decText, it2.started,todo.finished,date,time,priority,category
                    )
                }
            }
            val state = toDo?.let { it1 -> dbHandler.updateSingleToDo(it1) }

            println(state)
            startActivity(Intent(context , MainActivity::class.java))
        }







    }
}

private fun String.toString(category: String?) {

}
