package com.example.health

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class BookAppointmentActivity : AppCompatActivity() {

    private var ed1: EditText? = null
    private var ed2: EditText? = null
    private var ed3: EditText? = null
    private var ed4: EditText? = null
    private var tv: TextView? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var timePickerDialog: TimePickerDialog? = null
    private var dateButton: Button? = null
    private var timeButton: Button? = null
    private var btnBook: Button? = null
    private var btnBack: Button? = null

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        tv = findViewById(R.id.textViewBATitle)
        ed1 = findViewById(R.id.editTextBAFullName)
        ed2 = findViewById(R.id.editTextBAAddress)
        ed3 = findViewById(R.id.editTextBAContactNumber)
        ed4 = findViewById(R.id.editTextBAFees)
        dateButton = findViewById(R.id.buttonAppDatePicker)
        timeButton = findViewById(R.id.buttonAppTimePicker)
        btnBook = findViewById(R.id.buttonBABookAppointment)
        btnBack = findViewById(R.id.buttonBABack)

        ed1!!.keyListener = null
        ed2!!.keyListener = null
        ed3!!.keyListener = null
        ed4!!.keyListener = null

        val intent = intent
        val title = intent.getStringExtra("text1")
        val fullname = intent.getStringExtra("text2")
        val address = intent.getStringExtra("text3")
        val contact = intent.getStringExtra("text4")
        val fees = intent.getStringExtra("text5")

        tv?.text = title
        ed1!!.setText(fullname)
        ed2!!.setText(address)
        ed3!!.setText(contact)
        ed4!!.setText("Cons Fees:$fees/-")

// Rest of your code



        // datepicker
        initDatePicker()
        dateButton!!.setOnClickListener { datePickerDialog!!.show() }

        // timepicker
        initTimePicker()
        timeButton!!.setOnClickListener { timePickerDialog!!.show() }

        btnBack!!.setOnClickListener {
            startActivity(Intent(this@BookAppointmentActivity, FindDoctorActivity::class.java))
        }

        btnBook!!.setOnClickListener {
            val sharedpreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
            val username = sharedpreferences.getString("username", "").toString()
            val db = Database(applicationContext, "health", null, 1)

            if (db.checkAppointmentExists(
                    username,
                    "$title => $fullname",
                    address,
                    contact,
                    dateButton!!.text.toString(),
                    timeButton!!.text.toString()
                ) == 1
            ) {
                Toast.makeText(
                    applicationContext,
                    "Appointment already Booked !!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                db.addOrder(
                    username,
                    "$title => $fullname",
                    address,
                    contact,
                    0,
                    dateButton!!.text.toString(),
                    timeButton!!.text.toString(),
                    fees!!.toFloat(),
                    "appointment"
                )
                Toast.makeText(
                    applicationContext,
                    "Your appointment is done Successfully !!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this@BookAppointmentActivity, HomeActivity::class.java))
            }
        }
    }

    private fun initDatePicker() {
        val dateSetListener = OnDateSetListener { datePicker, i, i1, i2 ->
            var i1 = i1
            i1 = i1 + 1
            dateButton!!.text = "$i2/$i1/$i"
        }
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        val style = AlertDialog.THEME_HOLO_DARK
        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
        datePickerDialog!!.datePicker.minDate = cal.timeInMillis + 86400000
    }

    private fun initTimePicker() {
        val timeSetListener =
            OnTimeSetListener { timePicker, i, i1 -> timeButton!!.text = "$i:$i1" }
        val cal = Calendar.getInstance()
        val hrs = cal[Calendar.HOUR]
        val mins = cal[Calendar.MINUTE]
        val style = AlertDialog.THEME_HOLO_DARK
        timePickerDialog = TimePickerDialog(this, style, timeSetListener, hrs, mins, true)
    }
}
