package es.uca.singitloud.ui.salas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.uca.singitloud.R
import es.uca.singitloud.databinding.FragmentSalasBinding

class SalasFragment : Fragment() {

    private var _binding: FragmentSalasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val SalasViewModel =
            ViewModelProvider(this).get(SalasViewModel::class.java)

        _binding = FragmentSalasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val salas = listOf<Sala>(
            Sala("Sala 1", R.mipmap.imagen_sala1, "3", "4", "No"),
            Sala("Sala 2", R.mipmap.imagen_sala2, "4", "4", "Si"),
            Sala("Sala 3", R.mipmap.imagen_sala3, "4", "5", "No"),
            Sala("Sala 4", R.mipmap.imagen_sala4, "5", "5", "Si"),
            Sala("Sala 5", R.mipmap.imagen_sala5, "5", "6", "Si"),
            Sala("Sala 6", R.mipmap.imagen_sala6, "6", "7", "Si")
        )

        val recyclerView: RecyclerView = binding.recyclerView
        val context = requireContext()
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = SalaAdapter(salas)
        recyclerView.adapter = adapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}