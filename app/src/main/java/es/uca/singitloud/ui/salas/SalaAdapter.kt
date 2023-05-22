package es.uca.singitloud.ui.salas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.uca.singitloud.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.File
import java.io.FileOutputStream
import com.itextpdf.layout.element.Image
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject
import java.io.ByteArrayOutputStream

private lateinit var context: Context


class SalaAdapter(private val salas: List<Sala>) :
    RecyclerView.Adapter<SalaAdapter.SalaViewHolder>() {

    private lateinit var descargarPdfButton: View

    class SalaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreSala)
        val imgView: ImageView = itemView.findViewById(R.id.image)
        val descTextView: TextView = itemView.findViewById(R.id.descripcion)
        val tableLayout: TableLayout = itemView.findViewById(R.id.tabla)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalaViewHolder {
        context = parent.context

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_salas, parent, false)
        return SalaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SalaViewHolder, position: Int) {

        val currentSala = salas[position]

        holder.nombreTextView.text = currentSala.nombre
        holder.imgView.setImageResource(currentSala.img)
        holder.descTextView.text = currentSala.descripcion
        val tableLayout = holder.tableLayout

        tableLayout.removeAllViews()

        val encabezadosRow = TableRow(context)
        encabezadosRow.addView(createTableCell("Micrófonos"))
        encabezadosRow.addView(createTableCell("Personas"))
        encabezadosRow.addView(createTableCell("Dispone de sofá"))
        tableLayout.addView(encabezadosRow)

        val datosRow = TableRow(context)
        datosRow.addView(createTableCell(currentSala.micro))
        datosRow.addView(createTableCell(currentSala.personas))
        datosRow.addView(createTableCell(currentSala.sofa))
        tableLayout.addView(datosRow)

        val dividerDrawable = context.resources.getDrawable(R.drawable.divider)
        val backgroundDrawable = arrayOf(dividerDrawable)
        val layerDrawable = LayerDrawable(backgroundDrawable)
        encabezadosRow.background = layerDrawable
        datosRow.background = layerDrawable
    }

    private fun createTableCell(text: String): TextView {
        val cell = TextView(context)
        cell.text = text
        cell.gravity = Gravity.CENTER
        cell.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
        return cell
    }

    override fun getItemCount(): Int {
        return salas.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        descargarPdfButton = recyclerView.rootView.findViewById(R.id.descargarPdfButton)
        descargarPdfButton.setOnClickListener {
            generarYDescargarPdf(salas)
        }
    }

    private fun generarYDescargarPdf(salas: List<Sala>) {

        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
        val archivoPdf = File(path)

        if (!archivoPdf.exists()) archivoPdf.mkdirs()
        val archivo = File(archivoPdf, "salas.pdf")
        val fileOutputStream = FileOutputStream(archivo)

        val writer = PdfWriter(fileOutputStream)
        val pdf = PdfDocument(writer)
        val document = Document(pdf)

        try {
            document.add(Paragraph("Información de las salas:"))

            for (sala in salas) {
                document.add(Paragraph("Nombre: ${sala.nombre}"))
                document.add(Paragraph("Descripción: ${sala.descripcion}"))
                document.add(Paragraph("Número de micrófonos: ${sala.micro}"))
                document.add(Paragraph("Número de personas: ${sala.personas}"))
                document.add(Paragraph("Dispone de sofá: ${sala.sofa}"))
                document.add(Paragraph("-------------"))
            }

            document.close()

            MediaScannerConnection.scanFile(context, arrayOf(archivoPdf.path), null, null)

            Toast.makeText(context, "PDF descargado correctamente", Toast.LENGTH_SHORT).show()
            Log.d("PDF", "Ruta del archivo PDF: ${archivoPdf.absolutePath}")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al generar el PDF", Toast.LENGTH_SHORT).show()
        }
    }
}

