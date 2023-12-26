package com.example.tom

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Diction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.diction)

    }

    fun onTex1Click(view: View) {
        val intent = Intent(this, Bs::class.java)
        startActivity(intent)
    }
    fun onTex2Click(view: View) {
        val intent = Intent(this, Eb::class.java)
        startActivity(intent)
    }

    fun onTex4Click(view: View) {
        val intent = Intent(this, Lb::class.java)
        startActivity(intent)
    }
    fun onTex5Click(view: View) {
        val intent = Intent(this, Lm::class.java)
        startActivity(intent)
    }
    fun onTex6Click(view: View) {
        val intent = Intent(this,Pm::class.java)
        startActivity(intent)
    }
    fun onTex7Click(view: View) {
        val intent = Intent(this, Sl::class.java)
        startActivity(intent)
    }
    fun onTex8Click(view: View) {
        val intent = Intent(this, Sm::class.java)
        startActivity(intent)
    }
    fun onTex9Click(view: View) {
        val intent = Intent(this, Ts::class.java)
        startActivity(intent)
    }
    fun onTex10Click(view: View) {
        val intent = Intent(this, Tv::class.java)
        startActivity(intent)
    }
    fun onTex11Click(view: View) {
        val intent = Intent(this, Ty::class.java)
        startActivity(intent)
    }




}

