package com.cloudesire.partnercenter.entities

data class Address
(
        val addressLine1: String = "",

        val addressLine2: String? = null,

        val city: String? = null,

        val state: String? = null,

        val postalCode: String? = null,

        val country: String? = null,

        val region: String? = null,

        val firstName: String? = null,

        val lastName: String? = null,

        val phoneNumber: String? = null
)
