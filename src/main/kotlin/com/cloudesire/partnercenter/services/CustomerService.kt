package com.cloudesire.partnercenter.services

import com.cloudesire.partnercenter.entities.Customer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface CustomerService
{
    @POST("v1/customers")
    fun createCustomer(@Body customer: Customer)
            : Call<Customer>

    @DELETE("v1/customers/{customerId}")
    fun deleteCustomer(@Path("customerId") customerId: String)
            : Call<Void>
}
