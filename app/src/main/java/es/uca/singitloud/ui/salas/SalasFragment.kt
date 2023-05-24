package es.uca.singitloud.ui.salas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            Sala("Sala 1", R.mipmap.imagen_sala1,"Sala 1 es una sala de karaoke con capacidad de hasta 3 personas. Dispone de 2 micrófonos y una pantalla de 50 pulgadas. Excelente para disfrutar del karaoke con un grupo reducido de personas.","3", "4", "No"),
            Sala("Sala 2", R.mipmap.imagen_sala2,"Sala 2 es una sala de karaoke con capacidad de hasta 5 personas. Dispone de 4 micrófonos y una pantalla de 50 pulgadas. Excelente para disfrutar del karaoke por un precio reducido.", "4", "4", "Si"),
            Sala("Sala 3", R.mipmap.imagen_sala3,"Sala 3 es una sala de karaoke con capacidad de hasta 6 personas. Dispone de 6 micrófonos y una pantalla de 50 pulgadas. Excelente para disfrutar del karaoke con un grupo de personas que quiera disfrutar de una experiencia única sentados en el sofá.", "4", "5", "No"),
            Sala("Sala 4", R.mipmap.imagen_sala4, "Sala 4 es una sala de karaoke con capacidad de hasta 6 personas. Dispone de 6 micrófonos y una pantalla de 50 pulgadas. Excelente para disfrutar del karaoke con un grupo de personas que quiera disfrutar del karaoke con la máxima comodidad.","5", "5", "Si"),
            Sala("Sala 5", R.mipmap.imagen_sala5,"Sala 5 es una sala de karaoke con capacidad de hasta 7 personas. Dispone de 6 micrófonos y una pantalla de 50 pulgadas. Excelente para disfrutar del karaoke con un grupo de personas que quiera disfrutar de una experiencia única sentados en el sofá y con servicio de bar.", "5", "6", "Si"),
            Sala("Sala 6", R.mipmap.imagen_sala6,"Sala 6 es una sala de karaoke con capacidad de hasta 7 personas. Dispone de 7 micrófonos y una pantalla de 50 pulgadas. Excelente para disfrutar del karaoke con un grupo de personas que quiera disfrutar de una experiencia única sentados en el sofá y con servicio de bar. Ideal para celebrar fiestas", "6", "7", "Si")
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