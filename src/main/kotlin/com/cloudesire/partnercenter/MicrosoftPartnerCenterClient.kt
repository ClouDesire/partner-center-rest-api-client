package com.cloudesire.partnercenter

import com.cloudesire.partnercenter.clients.*
import com.cloudesire.partnercenter.services.*

class MicrosoftPartnerCenterClient
(
        private val username: String,
        private val password: String,
        private val clientId: String,
        private val resellerDomain: String
)
{
    private lateinit var serviceGenerator: ServiceGenerator

    private constructor(builder: Builder) : this(
            builder.username,
            builder.password,
            builder.clientId,
            builder.resellerDomain
    )
    {
        refreshToken()
    }

    class Builder
    {
        var username: String = ""
        var password: String = ""
        var clientId: String = ""
        var resellerDomain: String = ""

        fun username(username: String) = apply { this.username = username }
        fun password(password: String) = apply { this.password = password }
        fun clientId(clientId: String) = apply { this.clientId = clientId }
        fun resellerDomain(resellerDomain: String) = apply { this.resellerDomain = resellerDomain }

        fun build() = MicrosoftPartnerCenterClient(this)
    }

    fun refreshToken()
    {
        val authToken = AuthClient(username, password, clientId, resellerDomain).getAccessToken()
        serviceGenerator = ServiceGenerator(authToken)
    }

    fun getCustomerClient(): CustomerClient = CustomerClient(serviceGenerator.createService(CustomerService::class.java))

    fun getOrderClient(): OrderClient = OrderClient(serviceGenerator.createService(OrderService::class.java))

    fun getSubscriptionClient(): SubscriptionClient = SubscriptionClient(serviceGenerator.createService(SubscriptionService::class.java))

    fun getOfferClient(): OfferClient = OfferClient(serviceGenerator.createService(OfferService::class.java))
}
