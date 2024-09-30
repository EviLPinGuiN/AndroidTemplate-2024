package com.itis.template.domain.auth

class LoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        username: String,
        password: String
    ) = repository.login(username, password)
}