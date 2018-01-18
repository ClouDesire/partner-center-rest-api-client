package com.cloudesire.microsoftpartnercenter.services

import com.cloudesire.microsoftpartnercenter.entities.Order
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService
{
    @POST("v1/customers/{customerId}/orders")
    fun createOrder(@Path("customerId") customerId: String, @Body order: Order)
            : Call<Order>

    @GET("v1/customers/{customerId}/orders/{orderId}")
    fun retrieveOrder(@Path("customerId") customerId: String, @Path("orderId") orderId: String)
            : Call<Order>
}
