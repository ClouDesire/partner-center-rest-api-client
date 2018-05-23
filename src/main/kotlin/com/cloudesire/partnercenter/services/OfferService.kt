package com.cloudesire.partnercenter.services

import com.cloudesire.partnercenter.entities.Offer
import com.cloudesire.partnercenter.entities.Pagination
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OfferService
{
    @GET("v1/offers/{offerId}")
    fun retrieveOffer(@Path("offerId") orderId: String, @Query("country") country: String)
            : Call<Offer>

    @GET("v1/offers")
    fun retrieveOffers(@Query("country") country: String, @Query("offset") offset: Number,
                       @Query("size") size: Number)
            : Call<Pagination<Offer>>
}
