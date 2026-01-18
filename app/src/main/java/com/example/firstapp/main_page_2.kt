package com.example.firstapp

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class main_page_2 : AppCompatActivity() {

    private var branchh: String? = null
    private var semester: String? = null
    private var subject: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page2)

        // ================= STREAM =================
        val stream = intent.getStringExtra("stream") ?: run {
            Toast.makeText(this, "Stream not received", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // ================= UI =================
        val txtStream = findViewById<TextView>(R.id.txtStreamName)
        val imgStream = findViewById<ImageView>(R.id.imgStream)
        val txtBranch = findViewById<TextView>(R.id.txtBranch)
        val txtSemester = findViewById<TextView>(R.id.txtSemester)
        val txtSubject = findViewById<TextView>(R.id.txtSubject)
        val btnGetPdf = findViewById<Button>(R.id.btnGetPdf)

        val selectorBranch = findViewById<CardView>(R.id.selectorBranch)
        val selectorSemester = findViewById<CardView>(R.id.selectorSemester)
        val selectorSubject = findViewById<CardView>(R.id.selectorSubject)

        txtStream.text = stream
        imgStream.setImageResource(intent.getIntExtra("img", R.drawable.engg))

        // ================= LISTS =================
        val branches = mutableListOf<String>()
        val semesters = mutableListOf<String>()
        val subjects = mutableListOf<String>()

        // ================= RETROFIT =================
        val api = Retrofit.Builder()
            .baseUrl("https://one-night-study-backend.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        // ================= API 1 : STREAM → BRANCH =================
        api.getDataByStream(stream).enqueue(object : Callback<MyData> {

            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                if (!response.isSuccessful || response.body() == null) {
                    Log.e("API", "Branch load failed")
                    return
                }

                branches.clear()
                branches.addAll(response.body()!!)
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                Log.e("API", "Branch API failed", t)
            }
        })

        // ================= SELECT BRANCH =================
        selectorBranch.setOnClickListener {
            if (branches.isEmpty()) {
                Toast.makeText(this, "Loading branches...", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Select Branch")
                .setItems(branches.toTypedArray()) { _, which ->
                    branchh = branches[which]
                    txtBranch.text = branchh

                    semesters.clear()
                    subjects.clear()
                    txtSemester.text = "Select Semester"
                    txtSubject.text = "Select Subject"

                    api.getDataByBranch(stream, branchh!!)
                        .enqueue(object : Callback<MyData> {

                            override fun onResponse(
                                call: Call<MyData>,
                                response: Response<MyData>
                            ) {
                                semesters.clear()
                                semesters.addAll(response.body() ?: emptyList())
                            }

                            override fun onFailure(call: Call<MyData>, t: Throwable) {
                                Toast.makeText(
                                    this@main_page_2,
                                    "Error loading semesters",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
                .show()
        }

        // ================= SELECT SEMESTER =================
        selectorSemester.setOnClickListener {
            if (semesters.isEmpty()) {
                Toast.makeText(this, "Select branch first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Select Semester")
                .setItems(semesters.toTypedArray()) { _, which ->
                    semester = semesters[which]
                    txtSemester.text = semester

                    subjects.clear()
                    txtSubject.text = "Select Subject"

                    api.getDataBySem(stream, branchh!!, semester!!)
                        .enqueue(object : Callback<MyData> {

                            override fun onResponse(
                                call: Call<MyData>,
                                response: Response<MyData>
                            ) {
                                subjects.clear()
                                subjects.addAll(response.body() ?: emptyList())
                            }

                            override fun onFailure(call: Call<MyData>, t: Throwable) {
                                Toast.makeText(
                                    this@main_page_2,
                                    "Error loading subjects",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
                .show()
        }

        // ================= SELECT SUBJECT =================
        selectorSubject.setOnClickListener {
            if (subjects.isEmpty()) {
                Toast.makeText(this, "Select semester first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Select Subject")
                .setItems(subjects.toTypedArray()) { _, which ->
                    subject = subjects[which]
                    txtSubject.text = subject
                }
                .show()
        }

        // ================= GET PDF =================
        btnGetPdf.setOnClickListener {

            if (branchh == null || semester == null || subject == null) {
                Toast.makeText(this, "Please select all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            api.getDataBySub(stream, branchh!!, semester!!, subject!!)
                .enqueue(object : Callback<String> {

                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {
                        if (!response.isSuccessful || response.body().isNullOrBlank()) {
                            Toast.makeText(
                                this@main_page_2,
                                "PDF not found",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }

                        val pdfUrl = response.body()!!

                        startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
                        )
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(
                            this@main_page_2,
                            "Failed to open PDF",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }


    }
}
