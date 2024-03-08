package com.example.health

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity

class BuyMedicineActivity : AppCompatActivity() {

    private val packages = arrayOf(
        arrayOf("Uprise-D3 1000IU Capsule", "", "", "", "50"),
        arrayOf("HealthVit Chromium Picolinate 200mg Capsule", "", "", "", "305"),
        arrayOf("Vitamin B Complex Capsules", "", "", "", "899"),
        arrayOf("Infinite Vitamin E Wheat Germ Oil Capsule", "", "", "", "469"),
        arrayOf("Dolo 650 Tablet", "", "", "", "179"),
        arrayOf("Crocin 650 Advance Tablet", "", "", "", "90"),
        arrayOf("Strepsils Medicated Lozenges for Sore Throat", "", "", "", "40"),
        arrayOf("Tata 1mg Calcium + Vitamin D3", "", "", "", "67"),
        arrayOf("Feronia -XT Tablet", "", "", "", "139")
    )

    private val packageDetails = arrayOf(
        """
            Building and keeping the bones & teeth strong
            Reducing Fatigue/stress and muscular pains
            Boosting immunity and increasing resistance against infection
            """.trimIndent(),
        "Chromium is an essential trace mineral that plays an important role in helping insulin regulators",
        """
            Provides relief from Vitamin B deficiencies
            Helps in the formation of red blood cells
            Maintains a healthy nervous system
            """.trimIndent(),
        """
            It promotes health as well as skin benefit.
            It helps reduce skin blemishes and pigmentation.
            It acts as a safeguard for the skin from the harsh UVA and UVB sun rays.
            """.trimIndent(),
        "Dolo 650 Tablet helps relieve pain and fever by blocking the release of certain chemical messages",
        """
            Helps relieve fever and bring down a high temperature
            Suitable for people with a heart condition or high blood pressure
            """.trimIndent(),
        """
            Relieves the symptoms of a bacterial throat infection and soothes the recovery process
            Provides a warm and comforting feeling during a sore throat
            """.trimIndent(),
        """
            Reduces the risk of calcium deficiency, Rickets, Osteoporosis
            Promotes the mobility and flexibility of joints
            """.trimIndent(),
        "Helps to reduce iron deficiency due to chronic blood loss or low intake of iron"
    )

    private var item: HashMap<String, String>? = null
    private var list: ArrayList<HashMap<String, String>>? = null
    private var sa: SimpleAdapter? = null
    private var lst: ListView? = null
    private var btnBack: Button? = null
    private var btnGoToCart: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_medicine)

        lst = findViewById(R.id.listViewBM)

        // Directly set the click listeners without intermediate variables
        findViewById<Button>(R.id.buttonBMGoToCart).setOnClickListener {
            startActivity(Intent(this@BuyMedicineActivity, CartBuyMedicineActivity::class.java))
        }

        findViewById<Button>(R.id.buttonBMBack).setOnClickListener {
            startActivity(Intent(this@BuyMedicineActivity, HomeActivity::class.java))
        }

        list = ArrayList()
        for (i in packages.indices) {
            item = HashMap()
            item!!["line1"] = packages[i][0]
            item!!["line2"] = packages[i][1]
            item!!["line3"] = packages[i][2]
            item!!["line4"] = packages[i][3]
            item!!["line5"] = "Total Cost:${packages[i][4]}/-"
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

        lst!!.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this@BuyMedicineActivity, BuyMedicineDetailsActivity::class.java)
            intent.putExtra("text1", packages[i][0])
            intent.putExtra("text2", packageDetails[i])
            intent.putExtra("text3", packages[i][4])
            startActivity(intent)
        }
    }
}
