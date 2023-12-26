// ResultActivity.kt
package com.example.tom

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var resultImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result)

        resultImageView = findViewById(R.id.resultImageView)

        // 이전 화면에서 전달한 ByteArray 데이터를 받음
        val byteArray = intent?.getByteArrayExtra("resultImageByteArray")

        // ByteArray를 Bitmap으로 변환하여 결과 이미지뷰에 표시
        val resultBitmap = byteArray?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        }

        // 결과 이미지 설정
        resultImageView.setImageBitmap(resultBitmap)



        val homeButton: ImageButton = findViewById(R.id.Home)
        homeButton.setOnClickListener {
            navigateToHome()
        }

        val Diction: ImageButton = findViewById(R.id.Dic)
        Diction.setOnClickListener {
            navigateToDic()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToDic() {
        val intent = Intent(this, Diction::class.java)
        startActivity(intent)
    }
}
