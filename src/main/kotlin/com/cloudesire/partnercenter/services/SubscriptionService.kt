package com.cloudesire.partnercenter.services

import com.cloudesire.partnercenter.entities.Subscription
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SubscriptionService
{
    @GET("v1/customers/{customerId}/subscriptions/{subscriptionId}")
    fun retrieveSubscription(@Path("customerId") customerId: String, @Path("subscriptionId") subscriptionId: String)
            : Call<Subscription>

    @PATCH("v1/customers/{customerId}/subscriptions/{subscriptionId}")
    fun patchSubscription(@Path("customerId") customerId: String, @Path("subscriptionId") subscriptionId: String, @Body subscription: Subscription)
            : Call<Subscription>
}
