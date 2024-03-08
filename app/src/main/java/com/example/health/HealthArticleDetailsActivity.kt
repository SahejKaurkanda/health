package com.example.health

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HealthArticleDetailsActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var imgArticle: ImageView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_article_details)

        btnBack = findViewById(R.id.buttonHADBack)
        tvTitle = findViewById(R.id.textViewHADTitle)
        imgArticle = findViewById(R.id.imageView)

        val intent = intent
        tvTitle.text = intent.getStringExtra("text1")

        val bundle = intent.extras
        bundle?.let {
            val resId = it.getInt("text2")
            imgArticle.setImageResource(resId)
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this@HealthArticleDetailsActivity, HealthArticlesActivity::class.java))
        }
    }
}
