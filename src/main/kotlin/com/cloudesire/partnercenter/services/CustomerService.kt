package com.cloudesire.partnercenter.services

import com.cloudesire.partnercenter.entities.Agreement
import com.cloudesire.partnercenter.entities.Customer
import com.cloudesire.partnercenter.entities.Pagination
import retrofit2.Call
import retrofit2.http.*

interface CustomerService
{
    @POST("v1/customers")
    fun createCustomer(@Body customer: Customer)
            : Call<Customer>

    @GET("v1/agreements")
    fun retrieveAgreements()
            : Call<Pagination<Agreement>>

    @POST("v1/customers/{customerId}/agreements")
    fun acceptAgreement(@Path("customerId") customerId: String, @Body agreement: Agreement)
            : Call<Agreement>

    @GET("v1/customers/{customerId}")
    fun retrieveCustomer(@Path("customerId") customerId: String)
            : Call<Customer>

    @DELETE("v1/customers/{customerId}")
    fun deleteCustomer(@Path("customerId") customerId: String)
            : Call<Void>
}
