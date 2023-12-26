package com.example.tom

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

// BlogPost 데이터 클래스
data class BlogP(val id: Int, val title: String, val content: String)

// BlogApiService 인터페이스
interface BlogA {
    @GET("view_post")
    suspend fun getBlogPost(@Query("id") key: String): BlogP
}

class View : AppCompatActivity() {
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view)

        // 저장된 사용자 ID를 가져옴
        id = PreferenceUtil(applicationContext).getString(PreferenceUtil.ID, "")

        // UI 업데이트
        updateUI()

        val home: ImageButton = findViewById(R.id.gobak)
        home.setOnClickListener {
            Home()
        }
    }

    private fun updateUI() {
        // 코루틴을 사용하여 서버에서 블로그 포스트 가져오기
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Retrofit을 사용하여 서버에서 블로그 포스트 가져오기
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.0.107:5000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val blogApiService = retrofit.create(BlogA::class.java)

                // 서버에서 블로그 포스트 정보 가져오기
                val blogP = blogApiService.getBlogPost(key = id)

                // UI 업데이트
                updateUIWithBlogPost(blogP)
            } catch (e: Exception) {
                e.printStackTrace()
                // 예외 처리: 서버에서 블로그 포스트를 가져오지 못한 경우
            }
        }
    }

    private fun updateUIWithBlogPost(blogPost: BlogP) {
        // 블로그 포스트 정보를 UI에 업데이트
        val titleTextView: TextView = findViewById(R.id.titview)
        val contentTextView: TextView = findViewById(R.id.conview)

        titleTextView.text = blogPost.title
        contentTextView.text = blogPost.content
    }

    private fun Home() {
        val intent = Intent(this, Blog::class.java)
        startActivity(intent)
        finish()
    }
}
