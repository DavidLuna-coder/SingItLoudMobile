package es.uca.singitloud

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import es.uca.singitloud.ui.reservas.UserModel
import es.uca.singitloud.utils.ApiService
import es.uca.singitloud.utils.ReservaRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class UpdateFormActivity : AppCompatActivity() {
    private lateinit var textId : TextView
    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextFecha: EditText
    private lateinit var editTextHoraInicio: EditText
    private lateinit var editTextHoraFin: EditText
    private lateinit var editTextNumPersonas: EditText
    private lateinit var editTextNumSala: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonCancelar: Button

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_form)

        textId = findViewById(R.id.updateTextId)
        editTextNombre = findViewById(R.id.updateEditTextName)
        editTextApellido = findViewById(R.id.updateEditTextApellido)
        editTextFecha = findViewById(R.id.updateEditTextFecha)
        editTextHoraInicio = findViewById(R.id.updateEditTextHoraInicio)
        editTextHoraFin = findViewById(R.id.updateEditTextHoraFin)
        editTextNumPersonas = findViewById(R.id.updateEditTextNumPersonas)
        editTextNumSala = findViewById(R.id.updateEditTextNumSala)
        buttonGuardar = findViewById(R.id.updateButtonGuardar)
        buttonCancelar = findViewById(R.id.updateButtonCancelar)
        val extras = intent.extras
        val id = extras?.getString("id")
        val name = extras?.getString("name")
        val apellido = extras?.getString("apellido")
        val fecha = extras?.getString("fecha")
        val horaInicio = extras?.getString("horaInicio")
        val horaFin = extras?.getString("horaFin")
        val numPersonas = extras?.getString("numPersonas")
        val numSala = extras?.getString("numSala")

        textId.text = "Reserva: ${id}"
        editTextNombre.setText(name)
        editTextApellido.setText(apellido)
        editTextFecha.setText(fecha)
        editTextHoraInicio.setText(horaInicio)
        editTextHoraFin.setText(horaFin)
        editTextNumPersonas.setText(numPersonas)
        editTextNumSala.setText(numSala)


        editTextFecha.setOnClickListener {
            showDatePicker()
        }

        editTextHoraInicio.setOnClickListener {
            showTimePicker(editTextHoraInicio)
        }

        editTextHoraFin.setOnClickListener {
            showTimePicker(editTextHoraFin)
        }

        buttonGuardar.setOnClickListener {

            if(validateFields())  {
                val user = UserModel(
                    editTextNombre.text.toString().trim(),
                    editTextApellido.text.toString().trim()
                )
                val reservaRequest = ReservaRequestModel(
                    user,
                    editTextHoraInicio.text.toString().trim(),
                    editTextHoraFin.text.toString().trim(),
                    editTextFecha.text.toString().trim(),
                    editTextNumPersonas.text.toString().trim(),
                    editTextNumSala.text.toString().trim()
                )
                val context = this
                GlobalScope.launch(Dispatchers.IO) {
                    if(id != null)
                    {
                        if(putBooking(reservaRequest,context, id)){
                            withContext(Dispatchers.Main){
                                Toast.makeText(context, "Reserva actualizada", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            withContext(Dispatchers.Main){
                                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        withContext(Dispatchers.Main){
                            Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                        }
                    }

                    withContext(Dispatchers.Main){
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

        }

        buttonCancelar.setOnClickListener {
            // Regresa al MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val formattedDate = formatDate(selectedDate.time)
                editTextFecha.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun showTimePicker(editText: EditText) {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _: TimePicker, hourOfDay: Int, minute: Int ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                val formattedTime = formatTime(selectedTime.time)
                editText.setText(formattedTime)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun formatDate(date: Date): String {
        val dateFormat = android.text.format.DateFormat.getDateFormat(this)
        return dateFormat.format(date)
    }

    private fun formatTime(date: Date): String {
        val timeFormat = android.text.format.DateFormat.getTimeFormat(this)
        return timeFormat.format(date)
    }

    private fun validateFields(): Boolean {
        val nombre = editTextNombre.text.toString().trim()
        val apellido = editTextApellido.text.toString().trim()
        val fecha = editTextFecha.text.toString().trim()
        val horaInicio = editTextHoraInicio.text.toString().trim()
        val horaFin = editTextHoraFin.text.toString().trim()
        val numPersonas = editTextNumPersonas.text.toString().trim()
        val numSala = editTextNumSala.text.toString().trim()

        if (nombre.isEmpty()) {
            editTextNombre.error = "Ingrese un nombre"
            editTextNombre.requestFocus()
            return false
        }

        if (apellido.isEmpty()) {
            editTextApellido.error = "Ingrese un apellido"
            editTextApellido.requestFocus()
            return false
        }

        if (fecha.isEmpty()) {
            editTextFecha.error = "Seleccione una fecha"
            editTextFecha.requestFocus()
            return false
        }

        if (horaInicio.isEmpty()) {
            editTextHoraInicio.error = "Seleccione una hora de inicio"
            editTextHoraInicio.requestFocus()
            return false
        }

        if (horaFin.isEmpty()) {
            editTextHoraFin.error = "Seleccione una hora de fin"
            editTextHoraFin.requestFocus()
            return false
        }

        if (numPersonas.isEmpty()) {
            editTextNumPersonas.error = "Ingrese el número de personas"
            editTextNumPersonas.requestFocus()
            return false
        }

        if (numSala.isEmpty()) {
            editTextNumSala.error = "Ingrese el número de sala"
            editTextNumSala.requestFocus()
            return false
        }

        // Validación de la fecha de inicio no antes que hoy
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"))
        val currentDate = calendar.time
        val selectedDate = parseDate(fecha)
        if (selectedDate != null && selectedDate.before(currentDate)) {
            editTextFecha.error = "La fecha de inicio no puede ser antes que hoy"
            editTextFecha.requestFocus()
            return false
        }

        // Validación de la hora de inicio antes de la hora de fin
        val selectedTimeInicio = parseTime(horaInicio)
        val selectedTimeFin = parseTime(horaFin)
        if (selectedTimeInicio != null && selectedTimeFin != null && selectedTimeInicio >= selectedTimeFin) {
            editTextHoraInicio.error = "La hora de inicio debe ser anterior a la hora de fin"
            editTextHoraInicio.requestFocus()
            return false
        }

        return true
    }

    private fun parseDate(dateString: String): Date? {
        val format = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("GMT+1")
        return try {
            format.parse(dateString)
        } catch (e: ParseException) {
            null
        }
    }

    private fun parseTime(timeString: String): Date? {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return try {
            format.parse(timeString)
        } catch (e: ParseException) {
            null
        }
    }

    private suspend fun putBooking(request: ReservaRequestModel, context: Context, id:String) : Boolean{
        val service = ApiService(context)
        return service.putReserva(request, id)
    }
}