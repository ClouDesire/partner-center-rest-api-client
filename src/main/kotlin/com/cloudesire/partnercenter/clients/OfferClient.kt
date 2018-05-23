package com.cloudesire.partnercenter.clients

import com.cloudesire.partnercenter.entities.Offer
import com.cloudesire.partnercenter.entities.Pagination
import com.cloudesire.partnercenter.exceptions.EntityNotFoundException
import com.cloudesire.partnercenter.services.OfferService
import com.cloudesire.partnercenter.utils.RetrofitUtils

class OfferClient
(
        private val offerService: OfferService
)
{
    @Throws(EntityNotFoundException::class)
    fun retrieveOffer(offerId: String, country: String): Offer
    {
        val retrieveOfferCall = offerService
                .retrieveOffer(offerId, country)
                .execute()

        return retrieveOfferCall?.body()
               ?: throw EntityNotFoundException(RetrofitUtils.extractError(retrieveOfferCall))
    }

    @Throws(EntityNotFoundException::class)
    fun retrieveOffers(country: String, offset: Number, size: Number = 10): Pagination<Offer>
    {
        val retrieveOfferCall = offerService
                .retrieveOffers(country, offset, size)
                .execute()

        return retrieveOfferCall?.body()
               ?: throw EntityNotFoundException(RetrofitUtils.extractError(retrieveOfferCall))
    }
}
