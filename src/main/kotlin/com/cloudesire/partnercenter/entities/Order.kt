package com.cloudesire.partnercenter.entities

data class Order
(
        val id: String? = null,

        var referenceCustomerId: String = "",

        var lineItems: List<OrderLine> = arrayListOf(),

        val creationDate: String? = null
)
