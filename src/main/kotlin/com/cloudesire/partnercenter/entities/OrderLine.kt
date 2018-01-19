package com.cloudesire.partnercenter.entities

data class OrderLine
(
        var lineItemNumber: Int = 0,

        var offerId: String = "",

        var subscriptionId: String? = null,

        var parentSubscriptionId: String? = null,

        var quantity: Int = 1
)
