package es.uca.singitloud.ui.localizacion

import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import es.uca.singitloud.R
import es.uca.singitloud.databinding.FragmentLocalizacionBinding

class LocalizacionFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentLocalizacionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var map:GoogleMap
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

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return root
    }

    private fun createFragment(){
        val mapFragment: SupportMapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    private fun createMarker(){
        val coordinates = LatLng(36.530059, -6.196361)
        val marker = MarkerOptions().position(coordinates).title("SingitLoud")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f)
        )
    }
}