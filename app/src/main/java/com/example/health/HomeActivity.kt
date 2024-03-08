package com.example.health

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val sharedpreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
        val username = sharedpreferences.getString("username", "").toString()
        Toast.makeText(applicationContext, "Welcome $username", Toast.LENGTH_SHORT).show()
        val exit = findViewById<CardView>(R.id.cardExit)
        exit.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    LoginActivity::class.java
                )
            )
        }
        val findDoctor = findViewById<CardView>(R.id.cardFindDoctor)
        findDoctor.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    FindDoctorActivity::class.java
                )
            )
        }
        val labTest = findViewById<CardView>(R.id.cardLabTest)
        labTest.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    LabTestActivity::class.java
                )
            )
        }
        val orderDetails = findViewById<CardView>(R.id.cardOrderDetails)
        orderDetails.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    OrderDetailsActivity::class.java
                )
            )
        }
        val BuyMedicine = findViewById<CardView>(R.id.cardBuyMedicine)
        BuyMedicine.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    BuyMedicineActivity::class.java
                )
            )
        }
        val HealthArticles = findViewById<CardView>(R.id.cardHealthArticles)
        HealthArticles.setOnClickListener {
            startActivity(
                Intent(
                    this@HomeActivity,
                    HealthArticlesActivity::class.java
                )
            )
        }
    }
}