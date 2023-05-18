package es.uca.singitloud.ui.reservas


data class Reserva (var _id : String, var user : UserModel, var startTime: String, var endTime : String, var date: String, var numberOfPeople : Int, var room : Int ){
}