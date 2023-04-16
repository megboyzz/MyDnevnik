package ru.megboyzz.dnevnik.service


import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenService{

    @POST("/")
    suspend fun getToken(
        @Query("login") login: String,
        @Query("password") password: String,
        @Query("ReturnUrl") returnUrl: String = "https://login.dnevnik.ru/oauth2?response_type=token&client_id=bb97b3e445a340b9b9cab4b9ea0dbd6f&scope=CommonInfo,ContactInfo,FriendsAndRelatives,EducationalInfo"
    ): retrofit2.Call<ResponseBody>

}