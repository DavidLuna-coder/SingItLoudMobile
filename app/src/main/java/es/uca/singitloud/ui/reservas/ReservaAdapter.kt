package es.uca.singitloud.ui.reservas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import es.uca.singitloud.R
import es.uca.singitloud.UpdateFormActivity
import es.uca.singitloud.utils.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservaAdapter (private val reservas:List<Reserva>, private val recyclerView: RecyclerView): RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {
    class ReservaViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val nameTextView: TextView = itemView.findViewById(R.id.reserva_text_full_name)
        val idTextView: TextView = itemView.findViewById((R.id.reserva_text_id))
        val id = idTextView.text
        val startTimeTextView : TextView = itemView.findViewById(R.id.reserva_text_start_time)
        val endTimeTextView : TextView = itemView.findViewById(R.id.reserva_text_end_time)
        val dateTextView : TextView = itemView.findViewById(R.id.reserva_text_date)
        val numberOfPeopleTextView : TextView = itemView.findViewById(R.id.reserva_text_number_of_people)
        val roomTextView : TextView = itemView.findViewById(R.id.reserva_text_room)
        val verMasButton : Button = itemView.findViewById(R.id.reserva_button_ver_mas)
        val extraFields : RelativeLayout = itemView.findViewById(R.id.reserva_relative_2)
        val deleteButton : Button = itemView.findViewById(R.id.reserva_button_eliminar)
        val editButton : Button = itemView.findViewById(R.id.reserva_button_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_reserva,parent,false)
        return ReservaViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ReservaViewHolder,
        position: Int,
    ) {
        val currentReserva = reservas[position]
        holder.idTextView.text = currentReserva._id
        holder.nameTextView.text = "Nombre: " + (currentReserva.user.name + " " + currentReserva.user.lastName)
        holder.dateTextView.text = "Fecha: " + currentReserva.date
        holder.startTimeTextView.text = "Inicio: " +  currentReserva.startTime
        holder.endTimeTextView.text = "Fin: " + currentReserva.endTime
        holder.numberOfPeopleTextView.text = "Personas: " + currentReserva.numberOfPeople
        holder.roomTextView.text = "Sala: " + currentReserva.room

        holder.verMasButton.setOnClickListener {
            val gone = holder.extraFields.visibility == View.GONE
            if(gone){
                holder.extraFields.visibility = View.VISIBLE
                holder.verMasButton.text = "Ver menos"
            }else{
                holder.extraFields.visibility = View.GONE
                holder.verMasButton.text = "Ver Más"
            }
        }

        holder.editButton.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, UpdateFormActivity::class.java)
            intent.putExtra("id", currentReserva._id)
            intent.putExtra("name", currentReserva.user.name)
            intent.putExtra("apellido", currentReserva.user.lastName)
            intent.putExtra("fecha", currentReserva.date)
            intent.putExtra("horaInicio", currentReserva.startTime)
            intent.putExtra("horaFin",currentReserva.endTime)
            intent.putExtra("numPersonas", currentReserva.numberOfPeople)
            intent.putExtra("numSala", currentReserva.room)
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val apiService = ApiService(holder.itemView.context)
                val id = holder.idTextView.text as String
                val result : Boolean = apiService.deleteReserva(id)
                withContext(Dispatchers.Main){
                    val snack : Snackbar
                    if(result){
                        snack = Snackbar.make(it, "Eliminado con éxito", Snackbar.LENGTH_LONG)
                        snack.show()
                        val newReservas = reservas.filter { item -> item._id != id }
                        val adapter = ReservaAdapter(newReservas, recyclerView)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                    else{
                        snack = Snackbar.make(it, "Ha habido un error en la operación :(", Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return reservas.size
    }
}