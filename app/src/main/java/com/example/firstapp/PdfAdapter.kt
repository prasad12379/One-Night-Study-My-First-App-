import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.PdfModel
import com.example.firstapp.R

class PdfAdapter(private val pdfList: List<PdfModel>) :
    RecyclerView.Adapter<PdfAdapter.PdfViewHolder>() {

    class PdfViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.pdfTitle)
        val info: TextView = view.findViewById(R.id.pdfInfo)
        val price: TextView = view.findViewById(R.id.pdfPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pdf, parent, false)
        return PdfViewHolder(view)
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        val pdf = pdfList[position]
        holder.title.text = pdf.title
        holder.info.text = pdf.info
        holder.price.text = pdf.price
    }

    override fun getItemCount(): Int = pdfList.size
}