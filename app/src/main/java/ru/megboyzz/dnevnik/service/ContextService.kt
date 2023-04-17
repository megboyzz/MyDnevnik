package ru.megboyzz.dnevnik.service

import retrofit2.Call
import retrofit2.http.*
import ru.megboyzz.dnevnik.entities.response.UserContext

interface ContextService{

    @GET("/mobile/v4/users/{personId}/context")
    fun getContext(@Header("Access-Token") accessToken: String, @Path("personId") personId: String): Call<UserContext>

}