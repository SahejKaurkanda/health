package com.example.health

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class FindDoctorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_doctor)
        val exit = findViewById<CardView>(R.id.cardFDBack)
        exit.setOnClickListener {
            startActivity(
                Intent(
                    this@FindDoctorActivity,
                    HomeActivity::class.java
                )
            )
        }
        val familyphysician = findViewById<CardView>(R.id.cardFDFamilyPhysician)
        familyphysician.setOnClickListener {
            val it = Intent(this@FindDoctorActivity, DoctorDetailsActivity::class.java)
            it.putExtra("title", "Family Physicians")
            startActivity(it)
        }
        val dietician = findViewById<CardView>(R.id.cardFDDietician)
        dietician.setOnClickListener {
            val it = Intent(this@FindDoctorActivity, DoctorDetailsActivity::class.java)
            it.putExtra("title", "Dietician")
            startActivity(it)
        }
        val dentist = findViewById<CardView>(R.id.cardFDDentist)
        dentist.setOnClickListener {
            val it = Intent(this@FindDoctorActivity, DoctorDetailsActivity::class.java)
            it.putExtra("title", "Dentist")
            startActivity(it)
        }
        val surgeon = findViewById<CardView>(R.id.cardFDSurgeon)
        surgeon.setOnClickListener {
            val it = Intent(this@FindDoctorActivity, DoctorDetailsActivity::class.java)
            it.putExtra("title", "Surgeon")
            startActivity(it)
        }
        val cardiologists = findViewById<CardView>(R.id.cardFDCardiologists)
        cardiologists.setOnClickListener {
            val it = Intent(this@FindDoctorActivity, DoctorDetailsActivity::class.java)
            it.putExtra("title", "Cardiologist")
            startActivity(it)
        }
    }
}