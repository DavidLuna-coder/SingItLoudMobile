package es.uca.singitloud.ui.localizacion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocalizacionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Localizacion Fragment"
    }
    val text: LiveData<String> = _text
}