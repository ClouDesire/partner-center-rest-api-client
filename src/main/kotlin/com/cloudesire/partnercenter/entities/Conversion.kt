package com.cloudesire.partnercenter.entities

data class Conversion
(
        var subscriptionId: String = "",

        var offerId: String = "",

        var targetOfferId: String = "",

        var orderId: String = "",

        var quantity: Int = 1,

        var billingCycle: String = "Monthly"
)
