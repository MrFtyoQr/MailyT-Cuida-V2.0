package com.example.mrrobot

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.example.mrrobot.data.AppDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        database = AppDatabase.getDatabase(this)
        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
    }

    private fun setupListeners() {
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateInput(email, password)) {
                performLogin(email, password)
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            emailEditText.error = "El email es requerido"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Email inválido"
            return false
        }
        if (password.isEmpty()) {
            passwordEditText.error = "La contraseña es requerida"
            return false
        }
        if (password.length < 6) {
            passwordEditText.error = "La contraseña debe tener al menos 6 caracteres"
            return false
        }
        return true
    }

    private fun performLogin(email: String, password: String) {
        lifecycleScope.launch {
            val user = database.userDao().login(email, password)
            if (user != null) {
                Toast.makeText(this@MainActivity, "Login exitoso", Toast.LENGTH_SHORT).show()
                // TODO: Navegar a la siguiente pantalla
            } else {
                Toast.makeText(this@MainActivity, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}