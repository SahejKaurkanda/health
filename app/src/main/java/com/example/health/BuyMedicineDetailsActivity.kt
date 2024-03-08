package com.example.health

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BuyMedicineDetailsActivity : AppCompatActivity() {
    private lateinit var tvPackageName: TextView
    private lateinit var tvTotalCost: TextView
    private lateinit var edDetails: EditText
    private lateinit var btnAddToCart: Button
    private lateinit var btnBack: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_medicine_details)

        tvPackageName = findViewById(R.id.textViewBMDPackageDetails)
        tvTotalCost = findViewById(R.id.textViewBMDTotalCost)
        edDetails = findViewById(R.id.listViewBMDMultiLine)
        btnAddToCart = findViewById(R.id.buttonBMDAddToCart)
        btnBack = findViewById(R.id.buttonBMDBack)

        edDetails.keyListener = null

        val intent = intent
        tvPackageName.text = intent.getStringExtra("text1")
        edDetails.setText(intent.getStringExtra("text2"))
        tvTotalCost.text = "Total Cost:${intent.getStringExtra("text3")}/-"

        btnBack.setOnClickListener {
            startActivity(Intent(this@BuyMedicineDetailsActivity, BuyMedicineActivity::class.java))
        }

        btnAddToCart.setOnClickListener {
            val sharedpreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE)
            val username = sharedpreferences.getString("username", "").toString()
            val product = tvPackageName.text.toString()
            val price = intent.getStringExtra("text3").toString().toFloat()
            val db = Database(applicationContext, "health", null, 1)

            if (db.checkCart(username, product) == 1) {
                Toast.makeText(applicationContext, "Product Already Added!!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                db.addCart(username, product, price, "medicine")
                Toast.makeText(applicationContext, "Record Inserted to Cart", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(this@BuyMedicineDetailsActivity, BuyMedicineActivity::class.java))
            }
        }
    }
}
