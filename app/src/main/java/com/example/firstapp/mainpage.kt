package com.example.firstapp

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class mainpage : AppCompatActivity() {

    lateinit var firts_page_data : ArrayList<first_page_data_compact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage)
        val listview1=findViewById<ListView>(R.id.LV1)

        val stream=arrayOf("Engineering","Pharmacy","MBA","Polytechnic")
        val stream_img=intArrayOf(R.drawable.engg,R.drawable.pharm,R.drawable.mba,R.drawable.poly)
        val first_page_msg=arrayOf("Insem 2026 Quastions present for all sem" , "Insem 2026 Quastions present for all sem","Insem 2026 Quastions present for all sem","Insem 2026 Quastions present for all sem")
        firts_page_data=arrayListOf()
        for(i in stream.indices){
            val t1=stream[i]
            val t2=stream_img[i]
            val t3=first_page_msg[i]
            val data1= first_page_data_compact(t1,t2,t3)

            firts_page_data.add(data1)
        }
        val ad1=MyAdapter1(this,firts_page_data)
        listview1.adapter=ad1

        // user click on any stream he will redirect to second page

        listview1.setOnItemClickListener{parent, view, position, id ->
            val d1=stream[position]
            val d2=stream_img[position]

            val intent= Intent(this, main_page_2::class.java)
            intent.putExtra("stream",d1)
            intent.putExtra("img",d2)

            startActivity(intent)
        }

    }
}