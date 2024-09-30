package com.itis.template.domain.auth

class ValidateUserPasswordUseCase {

    operator fun invoke(
        password: String
    ): Boolean = password.checkLength() && password.checkFirstChar()

    private fun String.checkLength() = this.length > 5

    private fun String.checkFirstChar() = this[0].isUpperCase()
}