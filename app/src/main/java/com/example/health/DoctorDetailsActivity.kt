package com.example.health

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList
import java.util.HashMap

class DoctorDetailsActivity : AppCompatActivity() {

    private val doctorDetails1 = arrayOf(
        arrayOf("Doctor Name : Ajit Saste", "Hospital Address : Pimpri", "Exp : 5yrs", "Mobile No : 9898989898", "600"),
        arrayOf("Doctor Name : Prasad Pawar", "Hospital Address : Nigdi", "Exp : 15yrs ", "Mobile No : 7898989898", "900"),
        arrayOf("Doctor Name : Swapni Kale", "Hospital Address : Pune", "Exp : 8yrs", "Mobile No : 8898989898", "300"),
        arrayOf("Doctor Name : Deepak Deshmukh", "Hospital Address : Chichwad", "Exp : 6yrs", "Mobile No : 9898000000", "500"),
        arrayOf("Doctor Name : Ashok Pande", "Hospital Address : Katraj", "Exp : 7yrs", "Mobile No : 7798989898", "800")
    )

    private val doctorDetails2 = doctorDetails1
    private val doctorDetails3 = doctorDetails1
    private val doctorDetails4 = doctorDetails1
    private val doctorDetails5 = doctorDetails1

    private lateinit var tv: TextView
    private lateinit var btn: Button
    private lateinit var doctorDetails: Array<Array<String>>
    private lateinit var item: HashMap<String, String>
    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var sa: SimpleAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)

        tv = findViewById(R.id.textViewDDTitle)
        btn = findViewById(R.id.buttonDDBack)

        val intent = intent
        val title = intent.getStringExtra("title")
        tv.text = title

        doctorDetails = when (title) {
            "Family Physicians" -> doctorDetails1
            "Dietician" -> doctorDetails2
            "Dentist" -> doctorDetails3
            "Surgeon" -> doctorDetails4
            else -> doctorDetails1
        }

        btn.setOnClickListener {
            startActivity(Intent(this@DoctorDetailsActivity, FindDoctorActivity::class.java))
        }

        list = ArrayList()
        for (doctorDetail in doctorDetails) {
            item = HashMap()
            item["line1"] = doctorDetail[0]
            item["line2"] = doctorDetail[1]
            item["line3"] = doctorDetail[2]
            item["line4"] = doctorDetail[3]
            item["line5"] = "Cons Fees:" + doctorDetail[4] + "/-"
            list.add(item)
        }

        sa = SimpleAdapter(
            this, list,
            R.layout.multi_lines,
            arrayOf("line1", "line2", "line3", "line4", "line5"),
            intArrayOf(R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e)
        )

        val lst: ListView = findViewById(R.id.ListViewDD)
        lst.adapter = sa

        lst.setOnItemClickListener { parent, view, position, id ->
            val it = Intent(this@DoctorDetailsActivity, BookAppointmentActivity::class.java)
            it.putExtra("text1", title)
            it.putExtra("text2", doctorDetails[position][0])
            it.putExtra("text3", doctorDetails[position][1])
            it.putExtra("text4", doctorDetails[position][3])
            it.putExtra("text5", doctorDetails[position][4])
            startActivity(it)
        }
    }
}

