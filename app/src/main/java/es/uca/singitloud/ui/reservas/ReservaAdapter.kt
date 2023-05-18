package es.uca.singitloud.ui.reservas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.uca.singitloud.R

class ReservaAdapter (private val reservas:List<Reserva>): RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {
    class ReservaViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val nameTextView: TextView = itemView.findViewById(R.id.reserva_text_full_name)
        val idTextView: TextView = itemView.findViewById((R.id.reserva_text_id))
        val startTimeTextView : TextView = itemView.findViewById(R.id.reserva_text_start_time)
        val endTimeTextView : TextView = itemView.findViewById(R.id.reserva_text_end_time)
        val dateTextView : TextView = itemView.findViewById(R.id.reserva_text_date)
        val numberOfPeopleTextView : TextView = itemView.findViewById(R.id.reserva_text_number_of_people)
        val roomTextView : TextView = itemView.findViewById(R.id.reserva_text_room)
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
        holder.nameTextView.text = (currentReserva.user.name + currentReserva.user.lastName)
        holder.dateTextView.text = currentReserva.date
        holder.startTimeTextView.text = currentReserva.startTime
        holder.endTimeTextView.text = currentReserva.endTime
        holder.numberOfPeopleTextView.text = currentReserva.numberOfPeople.toString()
        holder.roomTextView.text = currentReserva.room.toString()
    }

    override fun getItemCount(): Int {
        return reservas.size
    }
}