package com.cloudesire.partnercenter.services

import com.cloudesire.partnercenter.entities.Customer
import retrofit2.Call
import retrofit2.http.*

interface CustomerService
{
    @POST("v1/customers")
    fun createCustomer(@Body customer: Customer)
            : Call<Customer>

    @GET("v1/customers/{customerId}")
    fun retrieveCustomer(@Path("customerId") customerId: String)
            : Call<Customer>

    @DELETE("v1/customers/{customerId}")
    fun deleteCustomer(@Path("customerId") customerId: String)
            : Call<Void>
}
