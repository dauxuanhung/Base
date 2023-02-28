package com.example.base.manager

import android.content.Context
import com.example.base.models.User
import com.squareup.moshi.Moshi

class PrefManager(context: Context, val moshi: Moshi) {

    private var perf = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val perfEdit = perf.edit()

    companion object {
        const val KEY_USER_INFO = "user-info"
        const val KEY_TOKEN = "access-token"
    }

    fun saveUserInfo(user: User?) {
        if (user == null) {
            perfEdit.putString(KEY_USER_INFO, null)
        } else {
            perfEdit.putString(KEY_USER_INFO, moshi.adapter(User::class.java).toJson(user))
        }
        perfEdit.apply()
    }

    fun clear() {
        saveAccessToken(null)
        saveUserInfo(null)
    }

    fun getUserInfo(): User? {
        val userJson = perf.getString(KEY_USER_INFO, null)
        return if (userJson.isNullOrEmpty()) {
            null
        } else {
            moshi.adapter(User::class.java).fromJson(userJson)
        }
    }

    fun saveAccessToken(token: String?) {
        perfEdit.putString(KEY_TOKEN, token)
        perfEdit.apply()
    }

    fun getAccessToken(): String? {
        return perf.getString(KEY_TOKEN, null)
    }
}