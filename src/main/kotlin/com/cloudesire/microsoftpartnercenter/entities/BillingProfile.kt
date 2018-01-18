package com.cloudesire.microsoftpartnercenter.entities

data class BillingProfile
(
        val email: String = "",

        val culture: String = "en-US",

        val language: String = "en",

        val companyName: String = "",

        val defaultAddress: Address = Address()
)
