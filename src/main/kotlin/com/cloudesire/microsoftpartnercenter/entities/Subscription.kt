package com.cloudesire.microsoftpartnercenter.entities

data class Subscription
(
        val id: String,

        val offerId: String?,

        val offerName: String?,

        var quantity: Int?,

        val unitType: String?,

        val creationDate: String?,

        val effectiveStartDate: String?,

        val commitmentEndDate: String?,

        var status: SubscriptionStatus?,

        val autoRenewEnabled: Boolean?,

        val billingType: String?,

        val billingCycle: String?,

        val suspensionReasons: List<String>?,

        val orderId: String?
)
{
    enum class SubscriptionStatus
    {
        active, deleted, none, suspended
    }
}
