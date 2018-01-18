package com.cloudesire.microsoftpartnercenter.entities

data class OrderLine
(
        val lineItemNumber: Int = 0,

        val offerId: String = "",

        val subscriptionId: String? = null,

        val parentSubscriptionId: String? = null,

        val quantity: Int = 1
)
