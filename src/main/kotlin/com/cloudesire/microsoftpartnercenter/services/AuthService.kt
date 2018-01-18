package com.cloudesire.microsoftpartnercenter.services

import com.cloudesire.microsoftpartnercenter.entities.AccessToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService
{
    @FormUrlEncoded
    @POST("{resellerDomain}/oauth2/token")
    fun getADToken(
            @Path("resellerDomain") resellerDomain: String,
            @Field("client_id") clientId: String,
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("resource") resource: String = "https://api.partnercenter.microsoft.com",
            @Field("grant_type") grantEntry: String = "password",
            @Field("scope") scope: String = "openid")
            : Call<AccessToken>

    @FormUrlEncoded
    @POST("generatetoken")
    fun getPartnerCenterToken(
            @Field("grant_type") grantType: String = "jwt_token")
            : Call<AccessToken>
}
