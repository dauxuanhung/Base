package com.example.base.manager

import com.example.base.models.User

open class UserManager(private val prefManager: PrefManager) {
    fun isLogged(): Boolean {
        return prefManager.getAccessToken() != null
    }

    fun logout() {
        prefManager.clear()
    }

    fun store(user: User, token: String) {
        prefManager.saveAccessToken(token)
        prefManager.saveUserInfo(user)
    }

    fun getAccessToken(): String = prefManager.getAccessToken().orEmpty()

    fun getUser(): User? = prefManager.getUserInfo()
}