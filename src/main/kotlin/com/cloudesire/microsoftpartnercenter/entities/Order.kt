package com.cloudesire.microsoftpartnercenter.entities

data class Order
(
        val id: String? = null,

        val referenceCustomerId: String = "",

        val lineItems: List<OrderLine> = arrayListOf(),

        val creationDate: String? = null
)
