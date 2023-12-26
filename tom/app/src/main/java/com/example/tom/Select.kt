package com.example.tom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Select : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select)

        val img1Button: ImageButton = findViewById(R.id.img1)
        img1Button.setOnClickListener {
            navigateToDetect1()
        }

        val img2Button: ImageButton = findViewById(R.id.img2)
        img2Button.setOnClickListener {
            navigateToDetect2()
        }

        val img3Button: ImageButton = findViewById(R.id.img3)
        img3Button.setOnClickListener {
            navigateToDetect3()
        }

        val img4Button: ImageButton = findViewById(R.id.img4)
        img4Button.setOnClickListener {
            navigateToDetect4()
        }

        val back: ImageButton = findViewById(R.id.back)
        back.setOnClickListener {
            navigateToHome()
        }
    }

    private fun navigateToDetect1() {
        val intent = Intent(this, Detect1::class.java)
        startActivity(intent)
    }
    private fun navigateToDetect2() {
        val intent = Intent(this, Detect2::class.java)
        startActivity(intent)
    }
    private fun navigateToDetect3() {
        val intent = Intent(this, Detect3::class.java)
        startActivity(intent)
    }
    private fun navigateToDetect4() {
        val intent = Intent(this, Detect4::class.java)
        startActivity(intent)
    }

    private fun navigateToHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

