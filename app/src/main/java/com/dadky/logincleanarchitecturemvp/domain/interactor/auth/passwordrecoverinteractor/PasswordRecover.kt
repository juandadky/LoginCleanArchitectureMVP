package com.dadky.logincleanarchitecturemvp.domain.interactor.auth.passwordrecoverinteractor

interface PasswordRecover {

    suspend fun sendPasswordResetEmail(email:String)
}