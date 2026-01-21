package com.example.firstapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)

        // 📦 Get username from SharedPreferences
        val sharedPref = requireActivity()
            .getSharedPreferences("USER_PREF", AppCompatActivity.MODE_PRIVATE)

        val username = sharedPref.getString("USERNAME", "User")
        tvUsername.text = username

        // My Purchased PDFs
        view.findViewById<TextView>(R.id.purchase_pdf).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MyPdfFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
