package com.example.database

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

import android.widget.DatePicker
import android.widget.TimePicker
import java.util.Calendar
import android.widget.ArrayAdapter;
import android.widget.Spinner;



class AddToDo : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var desc: EditText
    private lateinit var date: EditText
    private lateinit var priority :CheckBox
    private lateinit var add: Button
    private lateinit var dbHandler: DbHandler
    private lateinit var context: Context
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
        setContentView(R.layout.activity_add_to_do)



        title = findViewById(R.id.editToDoTextTitle)
        desc = findViewById(R.id.editToDoTextDescription)
        date = findViewById(R.id.editToDoDate)
        priority = findViewById(R.id.editToDoPriority)
        add = findViewById(R.id.buttonEdit)
        button1 = findViewById(R.id.radioButton)
        button2 = findViewById(R.id.radioButton2)
        button3 = findViewById(R.id.radioButton3)
        btnDatePicker=findViewById(R.id.btn_date);
        btnTimePicker=findViewById(R.id.btn_time);
        txtDate=findViewById(R.id.in_date);
        txtTime=findViewById(R.id.in_time);
        spinnerPriority=findViewById(R.id.spinner_priority)
        context = this
        val priorities = arrayOf("High", "Medium", "Low")


        dbHandler = DbHandler(context)

//        val adapter = ArrayAdapter.createFromResource(this, R.array.priorityLevels, android.R.layout.simple_spinner_item)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerPriority.adapter = adapter
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

        add.setOnClickListener(View.OnClickListener {
            val userTitle = title.text?.toString()
            val userDesc = desc.text?.toString()
            val userDate = date.text?.toString()
            val started = System.currentTimeMillis()
            val date = txtDate.text?.toString()
            val time = txtTime.text?.toString()



            if(userTitle!="" && userDesc!="" && date!="" && time!="" && priority!=""){
                val toDo = ToDo(userTitle, userDesc, started, 0,date,time,priority,category)

                dbHandler.addToDo(toDo)
                startActivity(Intent(context, MainActivity::class.java))

            }
            startActivity(Intent(context, MainActivity::class.java))
        })
    }
}