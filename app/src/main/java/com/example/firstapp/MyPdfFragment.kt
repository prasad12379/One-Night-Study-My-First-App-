package com.example.firstapp

import PdfLibraryModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MyPdfFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyText: TextView
    private lateinit var adapter: MyPdfAdapter
    private val pdfList = mutableListOf<PdfLibraryModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_my_pdf, container, false)

        recyclerView = view.findViewById(R.id.rvMyPdfs)
        emptyText = view.findViewById(R.id.tvEmptyLibrary)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MyPdfAdapter(pdfList) { pdf ->
            // 🔗 Open PDF
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(pdf.pdfUrl))
            )
        }
        recyclerView.adapter = adapter

        loadUserLibrary()

        return view
    }

    private fun loadUserLibrary() {

        val sharedPref =
            requireActivity().getSharedPreferences("USER_PREF", 0)
        val userKey = sharedPref.getString("USER_KEY", null)

        if (userKey == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val libraryRef = FirebaseDatabase
            .getInstance("https://database-3d487-default-rtdb.firebaseio.com/")
            .getReference("Users")
            .child(userKey)
            .child("library")

        libraryRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                pdfList.clear()

                if (!snapshot.exists()) {
                    emptyText.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    return
                }

                for (pdfSnap in snapshot.children) {
                    val pdf = pdfSnap.getValue(PdfLibraryModel::class.java)
                    if (pdf != null) {
                        pdfList.add(pdf)
                    }
                }

                emptyText.visibility =
                    if (pdfList.isEmpty()) View.VISIBLE else View.GONE
                recyclerView.visibility = View.VISIBLE

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Failed to load PDFs",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
