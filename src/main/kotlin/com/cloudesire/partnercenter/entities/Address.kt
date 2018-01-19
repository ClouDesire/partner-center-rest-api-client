package com.cloudesire.partnercenter.entities

data class Address
(
        var addressLine1: String = "",

        var addressLine2: String? = null,

        var city: String? = null,

        var state: String? = null,

        var postalCode: String? = null,

        var country: String? = null,

        var region: String? = null,

        var firstName: String? = null,

        var lastName: String? = null,

        var phoneNumber: String? = null
)
