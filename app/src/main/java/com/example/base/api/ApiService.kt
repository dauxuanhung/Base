package com.example.base.api

import com.example.base.base.BaseResponseApi
import com.example.base.models.ListResult
import com.example.base.models.Listing
import com.example.base.models.User
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST(ApiEndPoint.CREATE_USER)
    suspend fun register(@PartMap params: HashMap<String, RequestBody>): BaseResponseApi<User>

    @FormUrlEncoded
    @POST(ApiEndPoint.LOGIN)
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): BaseResponseApi<User>

    @Multipart
    @POST(ApiEndPoint.ACTIVE_ACCOUNT)
    suspend fun activeAccount(@Field("code") code: String): BaseResponseApi<User>

    @GET
    suspend fun getUserProfile(@Url url: String): BaseResponseApi<User>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_TRENDING)
    suspend fun getTrending(
        @Field("page") page: Int,
        @Field("limit") limit: Int
    ): BaseResponseApi<ListResult<Listing>>
}