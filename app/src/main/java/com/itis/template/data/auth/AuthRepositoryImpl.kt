package com.itis.template.data.auth

import com.itis.template.data.auth.datasource.AuthApi
import com.itis.template.data.auth.datasource.request.UserData
import com.itis.template.domain.auth.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(username: String, password: String) {
        authApi.login(
            UserData(
                phone = username,
                password = password
            )
        )
    }

    override suspend fun signOut() {
//        authApi.signOut()
    }


}