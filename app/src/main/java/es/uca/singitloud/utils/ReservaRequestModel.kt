package es.uca.singitloud.utils

import es.uca.singitloud.ui.reservas.UserModel
import kotlinx.serialization.Serializable

@Serializable
data class ReservaRequestModel (var user : UserModel, var startTime: String, var endTime : String, var date: String, var numberOfPeople : String, var room : String ){}
