package com.example.tom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Sign : AppCompatActivity() {

    private lateinit var apiManager: ApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign)

        apiManager = ApiManager()

        val signUpButton: ImageButton = findViewById(R.id.signUpButton)
        signUpButton.setOnClickListener {
            signUpButtonClicked()
        }
    }

    private suspend fun performSignUp(username: String, password: String) {
        val confirmPasswordEditText: EditText = findViewById(R.id.confirmPasswordEditText)
        val confirmPassword = confirmPasswordEditText.text.toString()

        // Check if the passwords match
        if (password == confirmPassword) {
            try {
                // Example: Use ApiManager to perform sign-up
                showLoading(true) // UI 업데이트: 로딩 시작
                val isSignUpSuccessful = apiManager.signUp(username, password)

                // Handle the result accordingly
                if (isSignUpSuccessful) {
                    showToast("회원가입 성공")
                    navigateToLoginActivity()
                } else {
                    showToast("회원가입 실패")
                }
            } catch (e: Exception) {
                showToast("회원가입 성공")
                e.printStackTrace()
                navigateToLoginActivity()
            } finally {
                showLoading(false) // UI 업데이트: 로딩 종료
            }
        } else {
            showToast("비밀번호가 일치하지 않습니다.")
        }
    }

    private fun signUpButtonClicked() {
        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)

        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        // TODO: Perform additional input validation if needed

        lifecycleScope.launch {
            performSignUp(username, password)
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish() // 현재 SignUpActivity를 종료합니다.
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        // TODO: UI 업데이트 - 로딩 상태에 따라 프로그레스 바 등을 제어하세요.
    }
}