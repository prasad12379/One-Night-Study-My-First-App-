package com.example.firstapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //lottie intro animation
        val lottie = findViewById<LottieAnimationView>(R.id.introimg)
        lottie.setAnimation("intro_animation.json")
        lottie.repeatCount = LottieDrawable.INFINITE
        lottie.playAnimation()

        val btn=findViewById<Button>(R.id.btn1)
        btn.setOnClickListener {
            val intent= Intent(applicationContext,signin::class.java)
            startActivity(intent)
        }
    }
}