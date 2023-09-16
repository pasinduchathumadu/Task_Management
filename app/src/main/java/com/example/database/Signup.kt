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

class Signup: AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignup: Button
    private lateinit var textViewLogin: TextView
    private lateinit var dbHandler: DbHandler
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_signup)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignup = findViewById(R.id.buttonSignup)
        textViewLogin = findViewById(R.id.textViewLogin)
        context = this
        dbHandler = DbHandler(context)

        buttonSignup.setOnClickListener(View.OnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
// null values not passes to the function
            if (email.isNotEmpty() && password.isNotEmpty()) {
                //call the user data class
                val userData = UserData(email, password)
                val success = dbHandler.addUser(userData)

                if (success) {
                    Toast.makeText(context, "Signup Successful!", Toast.LENGTH_SHORT).show()
                    // Redirect to the login page after successful signup
                    val intent = Intent(context, Login::class.java)
                    startActivity(intent)
                    finish() // Close the signup activity
                } else {
                    Toast.makeText(context, "Signup failed. Email already exists.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        })
//redirect to the login page
        textViewLogin.setOnClickListener {
            //create intent
            val intent = Intent(context, Login::class.java)
            startActivity(intent)
        }
    }
}
