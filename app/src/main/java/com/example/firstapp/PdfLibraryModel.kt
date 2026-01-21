data class PdfLibraryModel(
    val stream: String = "",
    val branch: String = "",
    val semester: String = "",
    val subject: String = "",
    val pdfUrl: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
