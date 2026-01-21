package com.example.firstapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signin : AppCompatActivity() {

    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val emailEt = findViewById<TextInputEditText>(R.id.ipEmail)
        val passEt = findViewById<TextInputEditText>(R.id.ipPassword)
        val btn = findViewById<Button>(R.id.btnSignIn)
        val btn2 = findViewById<TextView>(R.id.btnCreateAccount)

        database = FirebaseDatabase.getInstance("https://database-3d487-default-rtdb.firebaseio.com/").getReference("Users")

        btn.setOnClickListener {

            val email = emailEt.text.toString().trim()
            val pass = passEt.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                readdata(email, pass)
            }
        }

        btn2.setOnClickListener {
            startActivity(Intent(this, signup::class.java))
        }
    }

    private fun readdata(email: String, pass: String) {

        database.get().addOnSuccessListener {

            for (userSnap in it.children) {

                val dbEmail = userSnap.child("email").value.toString()
                val dbPass = userSnap.child("password").value.toString()
                val dbUsername = userSnap.child("username").value.toString()

                if (dbEmail == email && dbPass == pass) {

                    val userKey = userSnap.key  // 🔥 THIS IS THE FIX
                    val dbUsername = userSnap.child("username").value.toString()

                    val sharedPref = getSharedPreferences("USER_PREF", MODE_PRIVATE)
                    sharedPref.edit()
                        .putString("USER_KEY", userKey)
                        .putString("USERNAME", dbUsername)
                        .apply()

                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity_page1::class.java))
                    finish()
                    return@addOnSuccessListener
                }

            }

            Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
        }
    }

}
