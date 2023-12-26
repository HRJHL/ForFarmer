package com.example.tom

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    private val apiService: ApiService = createApiService()

    suspend fun login(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val requestBody = mapOf(
                "username" to username,
                "password" to password
            )
            val call = apiService.login(requestBody)
            try {
                val response = call.execute()
                response.isSuccessful && response.body()?.status == "success"
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun signUp(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val requestBody = mapOf(
                "username" to username,
                "password" to password
            )
            val call = apiService.signUp(requestBody)
            try {
                val response = call.execute()
                response.isSuccessful && response.body()?.status == "success"
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }


    private fun createApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.107:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
