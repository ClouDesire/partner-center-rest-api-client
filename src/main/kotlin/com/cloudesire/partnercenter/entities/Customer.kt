package com.cloudesire.partnercenter.entities

data class Customer
(
        val id: String? = null,

        var companyProfile: CompanyProfile = CompanyProfile(),

        var billingProfile: BillingProfile = BillingProfile(),

        var userCredentials: UserCredentials? = null
)
