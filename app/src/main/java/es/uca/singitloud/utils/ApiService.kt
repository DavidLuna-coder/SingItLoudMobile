package es.uca.singitloud.utils

import android.util.Log
import es.uca.singitloud.ui.reservas.Reserva
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import java.lang.Exception


class ApiService {
    private val client = HttpClient(Android){
        install(ContentNegotiation){
            json()
        }
    }

    suspend fun getReservas():List<Reserva>{
        Log.d("FETCH", "PRUEBA")
        try {
            val response: HttpResponse = client.get("http://192.168.0.7:3000/bookings")
            Log.d("PETICION", "getCall: ${response.status.toString()}")
            Log.d("LOG GETCALL", response.body())
            val reservas : List<Reserva> = response.body()
            Log.d("LOG GETCALL", reservas.toString())
            return reservas
        }catch (ex:Exception){
            Log.e("LOG GETCALL", "getCall: ERROR ${ex.message}" )
            return emptyList()
        }
    }
}