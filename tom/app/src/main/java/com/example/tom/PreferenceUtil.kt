package com.example.tom

import android.content.Context
import android.content.SharedPreferences
class PreferenceUtil(context: Context)
{
    private val prefs: SharedPreferences = context.getSharedPreferences("blogkey", Context.MODE_PRIVATE)

    companion object {
        const val KEY_BLOG = "blogkey"
        const val ID = "id"
    }
    //DB 넣기
    fun getString(key: String, defValue: String): String
    {
        return prefs.getString(key, defValue) ?: defValue
    }
    //DB 꺼내기
    fun setString(key: String, str: String)
    {
        prefs.edit().putString(key, str).apply()
    }

}