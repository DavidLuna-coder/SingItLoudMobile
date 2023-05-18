package es.uca.singitloud.ui.reservas

import kotlinx.serialization.Serializable

@Serializable
data class Reserva (var _id : String, var user : UserModel, var startTime: String, var endTime : String, var date: String, var numberOfPeople : String, var room : String ){
}