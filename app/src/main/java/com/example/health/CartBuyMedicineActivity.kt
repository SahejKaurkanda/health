package com.example.health

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CartBuyMedicineActivity : AppCompatActivity() {


    private lateinit var item: HashMap<String, String>
    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var sa: SimpleAdapter
    private lateinit var tvTotal: TextView
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var btnBack: Button
    private lateinit var btnCheckout: Button
    private lateinit var packages: Array<Array<String>>
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var lst: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_buy_medicine)

        dateButton = findViewById(R.id.buttonBMCartDatePicker)
        btnBack = findViewById(R.id.buttonBMCartBack)
        btnCheckout = findViewById(R.id.buttonBMCartCheckout)
        lst = findViewById(R.id.listViewBMCartMultiLine)
        tvTotal = findViewById(R.id.textViewBMCartTotalCost)

        val sharedpreferences =
            getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
        val username = sharedpreferences.getString("username", "").toString()

        val db = Database(applicationContext, "health", null, 1)

        var totalAmount = 0f
        val dbData = db.getCartData(username, "medicine")

        packages = Array(dbData.size) { Array(5) { "" } }

            for (i in dbData.indices) {
                val arrData = dbData[i].toString()
                val strData = arrData.split(java.util.regex.Pattern.quote("$").toRegex()).toTypedArray()
                packages[i][0] = strData[0]
                packages[i][4] = "Cost : " + strData[1] + "/-"
                totalAmount += strData[1].toFloat()
            }


            tvTotal!!.text = "Total Cost : $totalAmount"



            list = ArrayList()
            for (i in packages!!.indices) {
                item = HashMap()
                item!!["line1"] = packages!![i].getOrElse(0) { "" }
                item!!["line2"] = packages!![i].getOrElse(1) { "" }
                item!!["line3"] = packages!![i].getOrElse(2) { "" }
                item!!["line4"] = packages!![i].getOrElse(3) { "" }
                item!!["line5"] = packages!![i].getOrElse(4) { "" }
                list!!.add(item!!)
            }

            sa = SimpleAdapter(
                this, list,
                R.layout.multi_lines,
                arrayOf("line1", "line2", "line3", "line4", "line5"),
                intArrayOf(R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e)
            )
        lst.adapter = sa

        btnBack.setOnClickListener {
            startActivity(Intent(this@CartBuyMedicineActivity, LabTestActivity::class.java))
        }

        btnCheckout!!.setOnClickListener {
            val it = Intent(this@CartBuyMedicineActivity, BuyMedicineBookActivity::class.java)
            it.putExtra("price", tvTotal!!.text)
            it.putExtra("date", dateButton!!.text)
            startActivity(it)
        }

        // datepicker
        initDatePicker()
        dateButton!!.setOnClickListener { datePickerDialog!!.show() }
    }

    private fun initDatePicker() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
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
}



