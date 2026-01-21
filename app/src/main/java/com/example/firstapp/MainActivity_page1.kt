package com.example.firstapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.firstapp.AddFragment
import com.example.firstapp.MyPdfFragment
import com.example.firstapp.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity_page1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page1)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // Default fragment
        loadFragment(HomeFragment())

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_add -> {
                    // 🔥 OPEN ACTIVITY INSTEAD OF FRAGMENT
                    startActivity(Intent(this, mainpage::class.java))
                    false   // IMPORTANT
                }
                R.id.nav_pdfs -> loadFragment(MyPdfFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}