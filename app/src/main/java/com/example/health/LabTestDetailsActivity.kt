package com.example.health

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LabTestDetailsActivity : AppCompatActivity() {

    private lateinit var tvPackageName: TextView
    private lateinit var tvTotalCost: TextView
    private lateinit var edDetails: EditText
    private lateinit var btnAddToCart: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_test_details)

        tvPackageName = findViewById(R.id.textViewLTDPackageDetails)
        tvTotalCost = findViewById(R.id.textViewLTDTotalCost)
        edDetails = findViewById(R.id.listViewLTDMultiLine)
        btnAddToCart = findViewById(R.id.buttonLTDAddCart)
        btnBack = findViewById(R.id.buttonLTDGoBack)

        edDetails.keyListener = null

        val intent = intent
        tvPackageName.text = intent.getStringExtra("text1")
        edDetails.setText(intent.getStringExtra("text2"))
        tvTotalCost.text = "Total Cost:" + intent.getStringExtra("text3") + "/-"

        btnBack.setOnClickListener {
            startActivity(Intent(this@LabTestDetailsActivity, LabTestActivity::class.java))
        }

        btnAddToCart.setOnClickListener {
            val sharedpreferences =
                getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
            val username = sharedpreferences.getString("username", "").toString()
            val product = tvPackageName.text.toString()
            val price = intent.getStringExtra("text3")?.toFloatOrNull() ?: 0f

            val db = Database(applicationContext, "health", null, 1)

            if (db.checkCart(username, product) == 1) {
                Toast.makeText(applicationContext, "Product Already Added!!", Toast.LENGTH_SHORT).show()
            } else {
                db.addCart(username, product, price, "lab")
                Toast.makeText(applicationContext, "Record Inserted to Cart", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LabTestDetailsActivity, LabTestActivity::class.java))
            }
        }
    }
}
