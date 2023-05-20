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
import kotlinx.coroutines.NonDisposableHandle.parent

private lateinit var context: Context


class SalaAdapter(private val salas: List<Sala>) :
    RecyclerView.Adapter<SalaAdapter.SalaViewHolder>() {

    class SalaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreSala)
        val imgView: ImageView = itemView.findViewById(R.id.image)
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
        val tableLayout = holder.tableLayout

        tableLayout.removeAllViews()

        val encabezadosRow = TableRow(context)
        encabezadosRow.addView(createTableCell("Numero de micrófonos"))
        encabezadosRow.addView(createTableCell("Numero de personas"))
        encabezadosRow.addView(createTableCell("Dispone de sofá"))
        tableLayout.addView(encabezadosRow)

        val datosRow = TableRow(context)
        datosRow.addView(createTableCell(currentSala.micro))
        datosRow.addView(createTableCell(currentSala.personas))
        datosRow.addView(createTableCell(currentSala.sofa))
        tableLayout.addView(datosRow)
    }

    private fun createTableCell(text: String): TextView {
        val cell = TextView(context)
        cell.text = text
        return cell
    }

    override fun getItemCount(): Int {
        return salas.size
    }

    }
