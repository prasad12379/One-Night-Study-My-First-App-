package com.example.firstapp

import PdfLibraryModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyPdfAdapter(
    private val pdfList: List<PdfLibraryModel>,
    private val onItemClick: (PdfLibraryModel) -> Unit
) : RecyclerView.Adapter<MyPdfAdapter.PdfViewHolder>() {

    class PdfViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSubject: TextView = view.findViewById(R.id.tvSubject)
        val tvMeta: TextView = view.findViewById(R.id.tvMeta)
        val btnOpen: ImageView = view.findViewById(R.id.btnOpenPdf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_pdf, parent, false)
        return PdfViewHolder(view)
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        val pdf = pdfList[position]

        holder.tvSubject.text = pdf.subject
        holder.tvMeta.text =
            "${pdf.stream} • ${pdf.branch} • ${pdf.semester}"

        holder.btnOpen.setOnClickListener {
            onItemClick(pdf)
        }
    }

    override fun getItemCount(): Int = pdfList.size
}
