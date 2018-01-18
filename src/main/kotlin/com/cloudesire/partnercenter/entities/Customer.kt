package com.cloudesire.partnercenter.entities

data class Customer
(
        val id: String? = null,

        val companyProfile: CompanyProfile = CompanyProfile(),

        val billingProfile: BillingProfile = BillingProfile(),

        val userCredentials: UserCredentials? = null
)
