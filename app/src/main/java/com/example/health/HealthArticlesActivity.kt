package com.example.health

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

class HealthArticlesActivity : AppCompatActivity() {

    private val healthDetails = arrayOf(
        arrayOf("Walking Daily", "", "", "", "Click More Details"),
        arrayOf("Home care of COVID-19", "", "", "", "Click More Details"),
        arrayOf("Stop Smoking", "", "", "", "Click More Details"),
        arrayOf("Menstrual Cramps", "", "", "", "Click More Details"),
        arrayOf("Healthy Gut", "", "", "", "Click More Details")
    )

    private val images = intArrayOf(
        R.drawable.health1,
        R.drawable.health2,
        R.drawable.health3,
        R.drawable.health4,
        R.drawable.health5
    )

    private var item: HashMap<String, String>? = null
    private var list: ArrayList<HashMap<String, String>>? = null
    private var sa: SimpleAdapter? = null
    private var btnBack: Button? = null
    private var lst: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_articles)

        lst = findViewById(R.id.listViewHA)
        btnBack = findViewById(R.id.buttonHABack)

        // Store a local reference to btnBack
        val localBtnBack = btnBack

        localBtnBack?.setOnClickListener {
            startActivity(Intent(this@HealthArticlesActivity, HomeActivity::class.java))
        }

        list = ArrayList()
        for (i in healthDetails.indices) {
            item = HashMap()
            item!!["line1"] = healthDetails[i][0]
            item!!["line2"] = healthDetails[i][1]
            item!!["line3"] = healthDetails[i][2]
            item!!["line4"] = healthDetails[i][3]
            item!!["line5"] = healthDetails[i][4]
            list!!.add(item!!)
        }

        sa = SimpleAdapter(
            this,
            list,
            R.layout.multi_lines,
            arrayOf("line1", "line2", "line3", "line4", "line5"),
            intArrayOf(R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e)
        )

        lst!!.adapter = sa

        lst!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val intent = Intent(this@HealthArticlesActivity, HealthArticleDetailsActivity::class.java)
            intent.putExtra("text1", healthDetails[i][0])
            intent.putExtra("text2", images[i])
            startActivity(intent)
        }
    }
}
