package com.example.tom

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST

interface ApiService {

    // Login endpoint
    @POST("login")
    fun login(
        @Body requestBody: Map<String, String>
    ): Call<LoginResponse>

    @POST("signup")
    suspend fun signUp(
        @Body requestBody: Map<String, String>
    ): Call<SignUpResponse>
}
