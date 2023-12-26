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

// BlogPost data class
data class BlogPos(val id: Int, val title: String)

// BlogApiService interface
interface BlogApiService {
    @GET("blog_posts")
    suspend fun getBlogPosts(@Query("key") key: String): List<BlogPos>
}

class Blog : AppCompatActivity() {

    private lateinit var scrollView: ScrollView
    private lateinit var handler: Handler
    private val updateInterval: Long = 1000 // 1 second
    private lateinit var blogKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blog)

        scrollView = findViewById(R.id.scroll)
        blogKey = PreferenceUtil(applicationContext).getString(PreferenceUtil.KEY_BLOG, "")

        val write: ImageButton = findViewById(R.id.write)
        write.setOnClickListener {
            Write()
        }

        val back: ImageButton = findViewById(R.id.back)
        back.setOnClickListener {
            Back()
        }

        // Initialize the handler and schedule updates
        handler = Handler()
        scheduleUpdate()
    }

    private fun scheduleUpdate() {
        handler.postDelayed({
            // Retrofit을 사용하여 블로그 포스트 목록 가져오기
            GlobalScope.launch(Dispatchers.Main) {
                val blogPosts = getBlogPosts(blogKey)
                // 가져온 데이터를 사용하여 UI 업데이트
                showBlogPosts(blogPosts)
                // Reschedule the update after 1 second
                scheduleUpdate()
            }
        }, updateInterval)
    }

    private suspend fun getBlogPosts(blogKey: String): List<BlogPos> {
        return withContext(Dispatchers.IO) {
            try {
                // Retrofit을 사용하여 서버에서 블로그 포스트 목록 가져오기
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.0.107:5000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val blogApiService = retrofit.create(BlogApiService::class.java)

                // Pass the blog key as a query parameter
                blogApiService.getBlogPosts(key = blogKey)
            } catch (e: Exception) {
                // 예외 처리
                e.printStackTrace()
                emptyList() // 빈 목록 반환 또는 예외 처리에 따른 다른 방법 사용
            }
        }
    }

    private fun showBlogPosts(blogPosts: List<BlogPos>) {
        // 가져온 블로그 포스트 목록을 ScrollView에 추가
        runOnUiThread {
            scrollView.removeAllViews() // Clear existing views

            // Use LinearLayout as a container for multiple TextViews
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL
            for (blogPost in blogPosts) {
                val postTextView = TextView(this)
                // Set text size to 50dp
                postTextView.textSize = 30f

                // Set gravity to center
                postTextView.gravity = Gravity.CENTER

                val titleAndContent = "${blogPost.title}\n\n"
                postTextView.text = titleAndContent

                // Set OnClickListener for each TextView
                postTextView.setOnClickListener {
                    PreferenceUtil(applicationContext).setString(PreferenceUtil.ID, blogPost.id.toString())
                    onTitleClicked(blogPost.id)
                }

                linearLayout.addView(postTextView)
            }

            // Add LinearLayout to ScrollView
            scrollView.addView(linearLayout)
        }
    }

    private fun Write() {
        val intent = Intent(this, Write::class.java)
        startActivity(intent)
        finish()
    }

    private fun Back() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onTitleClicked(blogPostId: Int) {
        val intent = Intent(this, View::class.java)
        startActivity(intent)
        finish()
    }
}
