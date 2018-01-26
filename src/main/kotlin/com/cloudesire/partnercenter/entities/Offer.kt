package com.cloudesire.partnercenter.entities

data class Offer
(
        val id: String,

        val name: String,

        val minimumQuantity: Int,

        val maximumQuantity: Int,

        val isAvailableForPurchase: Boolean
)
