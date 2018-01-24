package com.cloudesire.partnercenter.entities

data class Conversion
(
        var subscriptionId: String = "",

        var offerId: String = "",

        var targetOfferId: String = "",

        var orderId: String = "",

        var billingCycle: String = "Monthly"
)
