package com.example.health


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern


class LabTestBookActivity : AppCompatActivity() {

    private lateinit var edname: EditText
    private lateinit var edaddress: EditText
    private lateinit var edcontact: EditText
    private lateinit var edpincode: EditText
    private lateinit var btnBooking: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_test_book)

        edname = findViewById(R.id.editTextLTBFullName)
        edaddress = findViewById(R.id.editTextLTBAddress)
        edcontact = findViewById(R.id.editTextLTBContactNumber)
        edpincode = findViewById(R.id.editTextLTBPincode)
        btnBooking = findViewById(R.id.buttonLTBBooking)

        val intent = intent
        val price = intent.getStringExtra("price")?.split(":")?.toTypedArray()
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")


        btnBooking.setOnClickListener {
            Log.d("LabTestBookActivity", "Button clicked")

            val sharedpreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
            val username = sharedpreferences.getString("username", "")
            val db = Database(applicationContext, "health", null, 1)

            if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(edname.text.toString())
                && !TextUtils.isEmpty(edaddress.text.toString())
                && !TextUtils.isEmpty(edcontact.text.toString())
                && !TextUtils.isEmpty(edpincode.text.toString())
                && date != null
                && time != null
                && price?.size == 2
            ) {
                db.addOrder(
                    username,
                    edname.text.toString(),
                    edaddress.text.toString(),
                    edcontact.text.toString(),
                    edpincode.text.toString().toInt(),
                    date,
                    time,
                    price[1].toFloat(),
                    "lab"
                )
                db.removeCart(username, "lab")
                Toast.makeText(
                    applicationContext,
                    "Your Booking is done Successfully !!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this@LabTestBookActivity, HomeActivity::class.java))
            } else {
                // Handle invalid input
                Toast.makeText(
                    applicationContext,
                    "Invalid input. Please fill in all fields.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
