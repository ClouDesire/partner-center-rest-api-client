package com.cloudesire.partnercenter.entities

data class ConversionResult
(
        val subscriptionId: String,

        val offerId: String,

        val targetOfferId: String,

        val error: ErrorResponse?
)
