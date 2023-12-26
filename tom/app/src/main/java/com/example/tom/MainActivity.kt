package com.example.tom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dicButton: ImageButton = findViewById(R.id.diction)
        dicButton.setOnClickListener {
            navigateToDiction()
        }

        val detButton: ImageButton = findViewById(R.id.detect)
        detButton.setOnClickListener {
            navigateToDetect()
        }

        val blogButton: ImageButton = findViewById(R.id.blog)
        blogButton.setOnClickListener {
            navigateToBlog()
        }
    }

    private fun navigateToDetect() {
        val intent = Intent(this, Select::class.java)
        startActivity(intent)
    }
    private fun navigateToDiction() {
        val intent = Intent(this, Diction::class.java)
        startActivity(intent)
    }
    private fun navigateToBlog() {
        val intent = Intent(this, Blog::class.java)
        startActivity(intent)
    }
}

