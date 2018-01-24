package com.cloudesire.partnercenter.services

import com.cloudesire.partnercenter.entities.Conversion
import com.cloudesire.partnercenter.entities.ConversionResult
import com.cloudesire.partnercenter.entities.Pagination
import com.cloudesire.partnercenter.entities.Subscription
import retrofit2.Call
import retrofit2.http.*

interface SubscriptionService
{
    @GET("v1/customers/{customerId}/subscriptions/{subscriptionId}")
    fun retrieveSubscription(@Path("customerId") customerId: String, @Path("subscriptionId") subscriptionId: String)
            : Call<Subscription>

    @GET("v1/customers/{customerId}/subscriptions")
    fun retrieveSubscriptions(@Path("customerId") customerId: String)
            : Call<Pagination<Subscription>>

    @PATCH("v1/customers/{customerId}/subscriptions/{subscriptionId}")
    fun patchSubscription(@Path("customerId") customerId: String, @Path("subscriptionId") subscriptionId: String, @Body subscription: Subscription)
            : Call<Subscription>

    @POST("v1/customers/{customerId}/subscriptions/{subscriptionId}/conversions")
    fun upgradeTrialToNormal(@Path("customerId") customerId: String, @Path("subscriptionId") subscriptionId: String, @Body conversion: Conversion)
            : Call<ConversionResult>
}
