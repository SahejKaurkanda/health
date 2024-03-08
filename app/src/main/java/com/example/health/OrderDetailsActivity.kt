package com.example.health

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

import java.util.ArrayList
import java.util.HashMap

class OrderDetailsActivity : AppCompatActivity() {

    private var order_details = arrayOf<Array<String>>()
    private lateinit var item: HashMap<String, String>
    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var lst: ListView
    private lateinit var btn: Button
    private lateinit var sa: SimpleAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        btn = findViewById(R.id.buttonODBack)
        lst = findViewById(R.id.listViewOD)

        btn.setOnClickListener {
            startActivity(Intent(this@OrderDetailsActivity, HomeActivity::class.java))
        }

        val db = Database(applicationContext, "health", null, 1)
        val sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
        val username = sharedpreferences.getString("username", "").toString()

        val dbData = db.getOrderData(username)

        order_details = Array(dbData.size) { Array(5) { "" } }
        for (i in order_details.indices) {
            val arrData = dbData[i].toString()
            val strData = arrData.split(java.util.regex.Pattern.quote("$").toRegex()).toTypedArray()
            order_details[i][0] = strData[0]
            order_details[i][1] = strData[1]
            order_details[i][2] = "Rs." + strData[6]

            println("strData: ${strData.joinToString()}") // Print strData for debugging

            if (strData.size > 7) {
                if (strData[7] == "medicine") {
                    order_details[i][3] = "Del:" + strData[4]
                } else if (strData[7] == "lab") {
                    order_details[i][3] = "Del:" + strData[4] + "" + strData[5]
                } else {
                    println("Unexpected value for otype: ${strData[7]}") // Print unexpected values
                }
                order_details[i][4] = strData[7]
            } else {
                println("Unexpected array size: ${strData.size}") // Print unexpected array size
            }

            list = ArrayList()
            for (i in order_details.indices) {
                item = HashMap()
                item["line1"] = order_details[i][0]
                item["line2"] = order_details[i][1]
                item["line3"] = order_details[i][2]
                item["line4"] = order_details[i][3]
                item["line5"] = order_details[i][4]
                list.add(item)
            }
            sa = SimpleAdapter(
                this, list,
                R.layout.multi_lines,
                arrayOf("line1", "line2", "line3", "line4", "line5"),
                intArrayOf(R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e)
            )
            lst.adapter = sa
        }
    }
}