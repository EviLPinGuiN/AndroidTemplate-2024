package com.itis.template.data.auth.datasource

import com.itis.template.data.auth.datasource.request.UserData
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST
    suspend fun login(@Body userData: UserData)
}