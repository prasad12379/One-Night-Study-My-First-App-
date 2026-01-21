package com.example.firstapp

import PdfAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.pdfRecycler)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val pdfList = listOf(
            PdfModel("Machine Learning", "Sem 7 · IT", "FREE"),
            PdfModel("DAA Important Qs", "Sem 5 · CSE", "₹49"),
            PdfModel("DBMS Notes", "Sem 5 · IT", "FREE"),
            PdfModel("Operating Systems", "Sem 4 · CSE", "₹29")
        )

        recyclerView.adapter = PdfAdapter(pdfList)

        return view
    }
}
