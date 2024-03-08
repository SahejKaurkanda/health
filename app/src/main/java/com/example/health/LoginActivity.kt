package com.example.health

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

import android.annotation.SuppressLint
import android.content.Context

class LoginActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edPassword: EditText
    private lateinit var btn: Button
    private lateinit var tv: TextView
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edUsername = findViewById(R.id.editTextLoginUsername)
        edPassword = findViewById(R.id.editTextLoginPassword)
        btn = findViewById(R.id.buttonLoginBack)
        tv = findViewById(R.id.textViewNewUser)
        auth = FirebaseAuth.getInstance()

        btn.setOnClickListener {
            val username = edUsername.text.toString()
            val password = edPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Firebase authentication
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Toast.makeText(applicationContext, "Login Success !!", Toast.LENGTH_SHORT).show()

                            // Save username to SharedPreferences
                            val sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
                            val editor = sharedpreferences.edit()
                            editor.putString("username", username)
                            editor.apply()

                            // Start HomeActivity
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        } else {
                            Toast.makeText(applicationContext, "Invalid Username and Password !!", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(applicationContext, "Please Fill All Details", Toast.LENGTH_SHORT).show()
            }
        }

        tv.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}
