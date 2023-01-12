package ru.megboyzz.dnevnik.entities.request

import com.google.gson.annotations.SerializedName

data class AuthorizationRequest(
    val username: String,
    val password: String,
    val scope: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String
)
