package com.cloudesire.microsoftpartnercenter

import com.cloudesire.microsoftpartnercenter.clients.AuthClient
import com.cloudesire.microsoftpartnercenter.clients.CustomerClient
import com.cloudesire.microsoftpartnercenter.clients.OrderClient
import com.cloudesire.microsoftpartnercenter.clients.SubscriptionClient
import com.cloudesire.microsoftpartnercenter.services.CustomerService
import com.cloudesire.microsoftpartnercenter.services.OrderService
import com.cloudesire.microsoftpartnercenter.services.ServiceGenerator
import com.cloudesire.microsoftpartnercenter.services.SubscriptionService

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
        val authToken = AuthClient(username, password, clientId, resellerDomain).getAccessToken()
        serviceGenerator = ServiceGenerator(authToken)
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

    fun getCustomerClient(): CustomerClient = CustomerClient(serviceGenerator.createService(CustomerService::class.java))

    fun getOrderClient(): OrderClient = OrderClient(serviceGenerator.createService(OrderService::class.java))

    fun getSubscriptionClient(): SubscriptionClient = SubscriptionClient(serviceGenerator.createService(SubscriptionService::class.java))
}
