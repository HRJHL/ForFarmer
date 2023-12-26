package com.example.tom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Handler

class Logo : AppCompatActivity() {

    private val delayMillis: Long = 3000 // 3초 딜레이

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logo)

        Handler().postDelayed({
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }, delayMillis)

    }
}