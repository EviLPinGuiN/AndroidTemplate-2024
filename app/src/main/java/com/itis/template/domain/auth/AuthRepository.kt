package com.itis.template.domain.auth

interface AuthRepository {

    suspend fun login(username: String, password: String)

    suspend fun signOut()
}