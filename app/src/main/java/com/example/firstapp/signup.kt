package com.example.firstapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {

    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val user = findViewById<TextInputEditText>(R.id.ipUsername)
        val mail = findViewById<TextInputEditText>(R.id.ipEmailR)
        val pass = findViewById<TextInputEditText>(R.id.ipPasswordR)
        val btn  = findViewById<Button>(R.id.btnSignUpR)

        database = FirebaseDatabase.getInstance("https://database-3d487-default-rtdb.firebaseio.com/").getReference("Users")

        btn.setOnClickListener {

            val username = user.text.toString().trim()
            val email = mail.text.toString().trim()
            val password = pass.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all the information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = database.push().key!!   // ✅ SAFE UNIQUE ID

            val data = compact(username, email, password)

            database.child(userId).setValue(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Registered successfully", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, signin::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
