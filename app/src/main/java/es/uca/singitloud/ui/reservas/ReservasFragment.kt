package es.uca.singitloud.ui.reservas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.uca.singitloud.databinding.FragmentReservasBinding
import es.uca.singitloud.utils.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservasFragment : Fragment() {

    private var _binding: FragmentReservasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(ReservasViewModel::class.java)

        _binding = FragmentReservasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome

        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        var reservas : List<Reserva>
        val recyclerView: RecyclerView = binding.recyclerView
        val context = requireContext()
        recyclerView.layoutManager = LinearLayoutManager(context)

        GlobalScope.launch(Dispatchers.IO) {
            reservas = fetchBookings()
            val adapter = ReservaAdapter(reservas)
            withContext(Dispatchers.Main){
                recyclerView.adapter = adapter
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    suspend private fun fetchBookings(): List<Reserva>{
        Thread.sleep(2000)
        /*var reservas = listOf(
            Reserva("1312", UserModel("David","Luna"), "13:00", "14:00", "2023-04-13", "4", 	"3"),
            Reserva("1352", UserModel("Laura","Guerrero"), "13:00", "14:00", "2023-04-13", "4", 	"3"),
            Reserva("4432", UserModel("Alemale","Malayo"), "13:00", "14:00", "2023-04-13", "4", 	"3"),
            Reserva("5532", UserModel("Juan","Perico"), "13:00", "14:00", "2023-04-13", "4", 	"3"),
        )*/
        Log.d("FETCH", "Función ejecutada");
        val service = ApiService()
        var reservas = service.getReservas()
        return reservas
    }
}