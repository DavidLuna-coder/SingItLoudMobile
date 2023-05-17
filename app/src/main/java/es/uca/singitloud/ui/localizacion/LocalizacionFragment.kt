package es.uca.singitloud.ui.localizacion

import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.uca.singitloud.databinding.FragmentLocalizacionBinding

class LocalizacionFragment : Fragment() {

    private var _binding: FragmentLocalizacionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLocalizacionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textLugar
        textView.text = "Nuestro karaoke se encuentra en Puerto Real y dispone de varios metodos de transporte como es el tren, autobus o taxi. " +
                "Para consultar los horarios de trenes acceda a: https://www.renfe.com/es/es"

        Linkify.addLinks(textView, Linkify.WEB_URLS)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}