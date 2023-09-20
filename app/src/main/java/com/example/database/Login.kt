package com.example.database

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Login : AppCompatActivity() {
    private lateinit var submit:Button
    private lateinit var signup:TextView
    private lateinit var editTextEmail:EditText
    private lateinit var editTextPassword:EditText
    private lateinit var dbHandler:DbHandler
    private lateinit var context:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        submit = findViewById(R.id.Login)
        signup = findViewById(R.id.textViewSignup)
        context = this
        dbHandler= DbHandler(context)
        submit.setOnClickListener(View.OnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            //check the nullable variable
            if (email.isNotEmpty() && password.isNotEmpty()) {
                //call the db handle function
                val isAuthenticated = dbHandler.authenticateUser(email, password)
                // it is also given true pass into this code
                if (isAuthenticated) {

                    val intent = Intent(context,MainActivity::class.java)
                    //shift to one task to another task
                    startActivity(intent)
                    finish()
                    // Redirect to the main activity or any other appropriate activity
                } else {
                    //this is the method to show the message
                    Toast.makeText(context, "Login failed. Incorrect email or password.", Toast.LENGTH_SHORT).show()
                    //Toast.LENGTH_SHORT ===> FOR SHORT TIME
                    //Toast.LENGTH_LONG ===> FOR LONG TIME
                    //First pass to the context (application context) after pass the text you want to set up and duration
                }
            } else {
                Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        })

// redirect to the signup page
        signup.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)

        })
    }
}