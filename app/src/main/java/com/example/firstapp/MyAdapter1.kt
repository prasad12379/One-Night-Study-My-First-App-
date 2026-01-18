package com.example.firstapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.core.Context

class MyAdapter1(val context: Activity,val firts_page_data: ArrayList<first_page_data_compact>):
    ArrayAdapter<first_page_data_compact>(context,R.layout.item_glass_list,firts_page_data){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater= LayoutInflater.from(context)  //for first parameter
        val view=inflater.inflate(R.layout.item_glass_list,null)

        val title=view.findViewById<TextView>(R.id.txtTitle)
        val msg=view.findViewById<TextView>(R.id.txtSubtitle)
        val img=view.findViewById<ImageView>(R.id.imgIcon)

        title.text=firts_page_data[position].stream
        msg.text=firts_page_data[position].first_page_msg
        img.setImageResource(firts_page_data[position].stream_img)

        return view
    }
}