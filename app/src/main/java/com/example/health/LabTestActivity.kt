package com.example.health

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

class LabTestActivity : AppCompatActivity() {
    private val packages = arrayOf(
        arrayOf("Package 1 : Full Body Checkup", "", "", "", "999"),
        arrayOf("Package 2 : Blood Glucose Fasting", "", "", "", "299"),
        arrayOf("Package 3 : COVID-19 Antibody-IgG", "", "", "", "899"),
        arrayOf("Package 4 : Thyroid Check", "", "", "", "499"),
        arrayOf("Package 5 : Immunity Check", "", "", "", "699")
    )

    private val packageDetails = arrayOf(
        """
            Blood Glucose Fasting
            Complete Hemogram
            HbA1c
            Iron Studies
            Kidney Function Test
            LDH Lactate Dehydrogenase, Serum
            Lipid profile
            Liver Function Test
            """.trimIndent(),
        "Blood Glucose Fasting",
        "COVID-19 Antibody-IgG",
        "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)",
        """
            Complete Hemogram
            CRP (C Reactive Protein) Quantitative, Serum
            Iron Studies
            Kidney Function Test
            Vitamin D Total-25 Hydroxy
            Liver Function Test
            Lipid profile
            """.trimIndent()
    )

    private lateinit var item: HashMap<String, String>
    private lateinit var list: ArrayList<HashMap<String, String>>
    private lateinit var sa: SimpleAdapter
    private lateinit var btnGoToCart: Button
    private lateinit var btnBack: Button
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_test)

        btnGoToCart = findViewById(R.id.buttonLTGoToCart)
        btnBack = findViewById(R.id.buttonLTBack)
        listView = findViewById(R.id.listViewLT)

        btnBack.setOnClickListener {
            startActivity(Intent(this@LabTestActivity, HomeActivity::class.java))
        }

        list = ArrayList()
        for (i in packages.indices) {
            item = HashMap()
            item["line1"] = packages[i][0]
            item["line2"] = packages[i][1]
            item["line3"] = packages[i][2]
            item["line4"] = packages[i][3]
            item["line5"] = "Total Cost:${packages[i][4]}/-"
            list.add(item)
        }

        sa = SimpleAdapter(
            this,
            list,
            R.layout.multi_lines,
            arrayOf("line1", "line2", "line3", "line4", "line5"),
            intArrayOf(R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e)
        )

        listView.adapter = sa

        listView.onItemClickListener =
            OnItemClickListener { _, _, i, _ ->
                val intent = Intent(this@LabTestActivity, LabTestDetailsActivity::class.java)
                intent.putExtra("text1", packages[i][0])
                intent.putExtra("text2", packageDetails[i])
                intent.putExtra("text3", packages[i][4])
                startActivity(intent)
            }

        btnGoToCart.setOnClickListener {
            startActivity(Intent(this@LabTestActivity, CartLabActivity::class.java))
        }
    }
}
