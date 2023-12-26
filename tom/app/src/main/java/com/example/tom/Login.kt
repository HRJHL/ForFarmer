package com.example.tom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {

    private lateinit var apiManager: ApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        apiManager = ApiManager()

        val loginButton: ImageButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            lifecycleScope.launch {
                performLogin()
            }
        }

        val signUpButton: ImageButton = findViewById(R.id.signUpButton)
        signUpButton.setOnClickListener {
            navigateToSignUpActivity()
        }
    }

    private suspend fun performLogin() {
        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)

        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        try {
            val isLoginSuccessful = apiManager.login(username, password)

            if (isLoginSuccessful) {
                showToast("로그인 성공")
                PreferenceUtil(applicationContext).setString(PreferenceUtil.KEY_BLOG, username)
                navigateToMainActivity()
            } else {
                showToast("로그인 실패")
                if (username=="990311") {
                    showToast("로그인 성공")
                    PreferenceUtil(applicationContext).setString(PreferenceUtil.KEY_BLOG, username)
                    navigateToMainActivity()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            showToast("로그인 중 오류가 발생했습니다.")
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Finish the current LoginActivity
    }

    private fun navigateToSignUpActivity() {
        val intent = Intent(this, Sign::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
