package com.cloudesire.partnercenter

import com.cloudesire.partnercenter.clients.*
import com.cloudesire.partnercenter.services.*

class MicrosoftPartnerCenterClient
(
        private val clientId: String,
        private val clientSecret: String,
        private val resellerDomain: String
)
{
    private lateinit var serviceGenerator: ServiceGenerator

    private constructor(builder: Builder) : this(
            builder.clientId,
            builder.clientSecret,
            builder.resellerDomain
    )
    {
        refreshToken()
    }

    class Builder
    {
        var clientId: String = ""
        var clientSecret: String = ""
        var resellerDomain: String = ""

        fun clientId(clientId: String) = apply { this.clientId = clientId }
        fun clientSecret(clientSecret: String) = apply { this.clientSecret = clientSecret }
        fun resellerDomain(resellerDomain: String) = apply { this.resellerDomain = resellerDomain }

        fun build() = MicrosoftPartnerCenterClient(this)
    }

    fun refreshToken()
    {
        val authToken = AuthClient(clientId, clientSecret, resellerDomain).getAccessToken()
        serviceGenerator = ServiceGenerator(authToken)
    }

    fun getCustomerClient(): CustomerClient = CustomerClient(serviceGenerator.createService(CustomerService::class.java))

    fun getOrderClient(): OrderClient = OrderClient(serviceGenerator.createService(OrderService::class.java))

    fun getSubscriptionClient(): SubscriptionClient = SubscriptionClient(serviceGenerator.createService(SubscriptionService::class.java))

    fun getOfferClient(): OfferClient = OfferClient(serviceGenerator.createService(OfferService::class.java))
}
