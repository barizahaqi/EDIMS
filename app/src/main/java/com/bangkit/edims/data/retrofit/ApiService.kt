package com.bangkit.edims.data.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("register")
    suspend fun signup(
        @Body signupRequest: SignupRequest
    ) : Response<SignupResponse>

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : Response<LoginResponse>

    @GET("items")
    suspend fun getProducts() : Response<ItemsResponse>

    @FormUrlEncoded
    @POST("item")
    suspend fun uploadProduct(
        @Part("name") name: RequestBody,
        @Part file : MultipartBody.Part,
        @Part("category") category: RequestBody,
        @Part("date") date : RequestBody
    ) : Response<UploadResponse>

    @DELETE("item/{id}")
    suspend fun deleteProducts(
        @Path("id") id : Int
    ) : Response<ErrorResponse>
}