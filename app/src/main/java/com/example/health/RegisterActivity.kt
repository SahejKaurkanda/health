package com.example.health

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class RegisterActivity : AppCompatActivity() {

    private lateinit var edUsername: EditText
    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var edConfirm: EditText
    private lateinit var btn: Button
    private lateinit var tv: TextView
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edUsername = findViewById(R.id.editTextAppFullName)
        edPassword = findViewById(R.id.editTextAppPassword)
        edEmail = findViewById(R.id.editTextAppEmail)
        edConfirm = findViewById(R.id.editTextAppConfirmPassword)
        btn = findViewById(R.id.buttonRegister)
        tv = findViewById(R.id.textViewExistingUser)
        auth = FirebaseAuth.getInstance()

        tv.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }

        btn.setOnClickListener {
            val username = edUsername.text.toString()
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()
            val confirm = edConfirm.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(applicationContext, "Please Fill All Details", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (password == confirm) {
                    if (isValid(password)) {
                        // Firebase authentication
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Registration successful
                                    Toast.makeText(
                                        applicationContext,
                                        "Record Inserted!!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(
                                        Intent(
                                            this@RegisterActivity,
                                            LoginActivity::class.java
                                        )
                                    )
                                } else {
                                    // Check for specific exception types
                                    when (task.exception) {
                                        is FirebaseAuthUserCollisionException -> {
                                            // Email address is already in use
                                            Toast.makeText(
                                                applicationContext,
                                                "The email address is already in use by another account.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        else -> {
                                            // General registration failure
                                            Log.e(
                                                "RegisterActivity",
                                                "Registration failed",
                                                task.exception
                                            )
                                            Toast.makeText(
                                                applicationContext,
                                                "Registration failed. Please try again.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Password must contain at least 8 characters, having a letter, digit, and special symbol!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Password and Confirm Password didn't match!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        fun isValid(passwordhere: String): Boolean {
            var f1 = 0
            var f2 = 0
            var f3 = 0
            if (passwordhere.length < 8) {
                return false
            } else {
                for (p in passwordhere.indices) {
                    if (Character.isLetter(passwordhere[p])) {
                        f1 = 1
                    }
                }
                for (r in passwordhere.indices) {
                    if (Character.isDigit(passwordhere[r])) {
                        f2 = 1
                    }
                }
                for (s in passwordhere.indices) {
                    if (!Character.isLetterOrDigit(passwordhere[s])) {
                        f3 = 1
                    }
                }
                return f1 == 1 && f2 == 1 && f3 == 1
            }
        }
    }
}
