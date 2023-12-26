package com.example.tom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class BlogPost(val title: String, val content: String, val blogKey: String)

interface BlogApi {
    @Multipart
    @POST("blogbox")
    fun sendBlogPost(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("blogKey") blogKey: RequestBody,
    ): Call<ResponseBody>
}

class Write : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.107:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val blogApi = retrofit.create(BlogApi::class.java)

        val back: ImageButton = findViewById(R.id.back)
        val writetitle: EditText = findViewById(R.id.writetitle)
        val writebox: EditText = findViewById(R.id.writebox)
        val writeButton: ImageButton = findViewById(R.id.write)

        writeButton.setOnClickListener {
            val title = writetitle.text.toString()
            val content = writebox.text.toString()
            val blogKey = PreferenceUtil(applicationContext).getString(PreferenceUtil.KEY_BLOG, "")

            val titleRequestBody = RequestBody.create("text/plain".toMediaType(), title)
            val contentRequestBody = RequestBody.create("text/plain".toMediaType(), content)
            val blogKeyRequestBody = RequestBody.create("text/plain".toMediaType(), blogKey)

            val call = blogApi.sendBlogPost(titleRequestBody, contentRequestBody, blogKeyRequestBody)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    // Handle success
                    if (response.isSuccessful) {
                        WWW()
                    } else {
                        // Handle error
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Handle failure
                }
            })
        }
        back.setOnClickListener {
            Blog()
        }
    }
    private fun Blog() {
        val intent = Intent(this, Blog::class.java)
        startActivity(intent)
        finish()
    }

    private fun WWW() {
        val intent = Intent(this, Blog::class.java)
        startActivity(intent)
        finish()
    }
}
