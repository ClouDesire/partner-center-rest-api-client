package com.cloudesire.partnercenter.clients

import com.cloudesire.partnercenter.entities.Subscription
import com.cloudesire.partnercenter.exceptions.EntityNotFoundException
import com.cloudesire.partnercenter.exceptions.InvalidActionException
import com.cloudesire.partnercenter.services.SubscriptionService
import com.cloudesire.partnercenter.utils.RetrofitErrorConverter

class SubscriptionClient
(
        private val subscriptionService: SubscriptionService
)
{
    fun retrieveSubscription(customerId: String, subscriptionId: String): Subscription
    {
        val retrieveSubscriptionCall = subscriptionService
                .retrieveSubscription(customerId, subscriptionId)
                .execute()

        return retrieveSubscriptionCall?.body()
               ?: throw EntityNotFoundException(RetrofitErrorConverter.toString(retrieveSubscriptionCall.errorBody()))
    }

    private fun patchSubscription(customerId: String, subscription: Subscription): Subscription
    {
        val patchSubscriptionCall = subscriptionService
                .patchSubscription(customerId, subscription.id, subscription)
                .execute()

        return patchSubscriptionCall?.body()
               ?: throw InvalidActionException(RetrofitErrorConverter.toString(patchSubscriptionCall.errorBody()))
    }

    fun suspendSubscription(customerId: String, subscriptionId: String): Subscription
    {
        val subscription = retrieveSubscription(customerId, subscriptionId)
        subscription.status = Subscription.SubscriptionStatus.suspended
        return patchSubscription(customerId, subscription)
    }

    fun activateSubscription(customerId: String, subscriptionId: String): Subscription
    {
        val subscription = retrieveSubscription(customerId, subscriptionId)
        subscription.status = Subscription.SubscriptionStatus.active
        return patchSubscription(customerId, subscription)
    }

    fun updateSubscriptionQuantity(customerId: String, subscriptionId: String, quantity: Int): Subscription
    {
        val subscription = retrieveSubscription(customerId, subscriptionId)
        subscription.quantity = quantity
        return patchSubscription(customerId, subscription)
    }
}