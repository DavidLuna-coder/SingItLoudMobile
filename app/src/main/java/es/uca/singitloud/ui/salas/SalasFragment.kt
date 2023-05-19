package es.uca.singitloud.ui.salas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        val firstInfoBlock: LinearLayout = binding.firstInfoBlock
        val secondInfoBlock: LinearLayout = binding.secondInfoBlock

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}