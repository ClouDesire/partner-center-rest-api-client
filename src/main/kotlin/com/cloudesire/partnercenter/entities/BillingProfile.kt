package com.cloudesire.partnercenter.entities

data class BillingProfile
(
        var email: String = "",

        var culture: String = "en-US",

        var language: String = "en",

        var companyName: String = "",

        var defaultAddress: Address = Address()
)
