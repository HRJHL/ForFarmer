package com.example.tom

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Streaming
import java.io.ByteArrayOutputStream

interface ApiService1 {
    @Multipart
    @POST("upload_predict1")
    suspend fun uploadAndPredict(@Part image: MultipartBody.Part): ResponseBody

    @GET("get_result_image")
    @Streaming
    suspend fun getResultImage(): ResponseBody
}

class Detect1 : AppCompatActivity() {

    private val PICK_IMAGE = 1
    private lateinit var selectedImage: Bitmap
    private lateinit var imageView: ImageView
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.107:5000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService1::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detect1)

        imageView = findViewById(R.id.imageView)

        val galleryButton: ImageButton = findViewById(R.id.galleryButton)
        galleryButton.setOnClickListener {
            openGallery()
        }

        val uploadButton: ImageButton = findViewById(R.id.uploadButton)
        uploadButton.setOnClickListener {
            uploadAndNavigateToResultPage()
        }


    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE)
    }

    private fun compressAndResizeImage(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        return stream.toByteArray()
    }

    private fun uploadAndNavigateToResultPage() {
        if (::selectedImage.isInitialized) {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val byteArray = compressAndResizeImage(selectedImage)
                    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
                    val body = MultipartBody.Part.createFormData("file", "image.jpg", requestFile)

                    val response = apiService.uploadAndPredict(body)
                    val resultImageBytes = response.bytes()
                    navigateToResultPage(resultImageBytes)
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast("Upload and navigation to result page failed: ${e.message}")
                }
            }
        }
    }

    private suspend fun getResultImage() {
        try {
            val resultImageResponse = apiService.getResultImage()
            val resultImageBytes = resultImageResponse.bytes()

            val resultBitmap = BitmapFactory.decodeByteArray(resultImageBytes, 0, resultImageBytes.size)
            // TODO: 결과 이미지를 원하는 방식으로 표시

        } catch (e: Exception) {
            e.printStackTrace()
            showToast("Failed to get result image: ${e.message}")
        }
    }

    private fun navigateToResultPage(resultImageBytes: ByteArray) {
        try {
            val resultIntent = Intent(this@Detect1, ResultActivity::class.java)
            resultIntent.putExtra("resultImageByteArray", resultImageBytes)
            startActivity(resultIntent)
        } catch (e: Exception) {
            e.printStackTrace()
            showToast("Navigation to result page failed: ${e.message}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE -> {
                    val uri = data?.data
                    selectedImage = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    imageView.setImageBitmap(selectedImage)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
