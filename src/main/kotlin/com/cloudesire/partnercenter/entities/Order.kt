package com.cloudesire.partnercenter.entities

data class Order
(
        val id: String? = null,

        val referenceCustomerId: String = "",

        val lineItems: List<OrderLine> = arrayListOf(),

        val creationDate: String? = null
)
