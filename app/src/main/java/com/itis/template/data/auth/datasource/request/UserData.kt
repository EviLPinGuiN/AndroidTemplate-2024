package com.itis.template.data.auth.datasource.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    @SerialName("username")
    val phone: String,
    @SerialName("pass")
    val password: String
)