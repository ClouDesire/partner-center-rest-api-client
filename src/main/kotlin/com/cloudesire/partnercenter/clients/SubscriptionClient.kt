package com.cloudesire.partnercenter.clients

import com.cloudesire.partnercenter.entities.Conversion
import com.cloudesire.partnercenter.entities.Pagination
import com.cloudesire.partnercenter.entities.Subscription
import com.cloudesire.partnercenter.exceptions.EntityNotFoundException
import com.cloudesire.partnercenter.exceptions.InvalidActionException
import com.cloudesire.partnercenter.services.SubscriptionService
import com.cloudesire.partnercenter.utils.RetrofitUtils

class SubscriptionClient
(
        private val subscriptionService: SubscriptionService
)
{
    @Throws(EntityNotFoundException::class)
    fun retrieveSubscription(customerId: String, subscriptionId: String): Subscription
    {
        val retrieveSubscriptionCall = subscriptionService
                .retrieveSubscription(customerId, subscriptionId)
                .execute()

        return retrieveSubscriptionCall?.body()
               ?: throw EntityNotFoundException(RetrofitUtils.extractError(retrieveSubscriptionCall))
    }

    @Throws(EntityNotFoundException::class)
    fun retrieveSubscriptionsByOrderId(customerId: String, orderId: String): Pagination<Subscription>
    {
        val retrieveSubscriptionsByOrderIdCall = subscriptionService
                .retrieveSubscriptionsByOrderId(customerId, orderId)
                .execute()

        return retrieveSubscriptionsByOrderIdCall?.body()
               ?: throw EntityNotFoundException(RetrofitUtils.extractError(retrieveSubscriptionsByOrderIdCall))
    }

    @Throws(EntityNotFoundException::class)
    fun retrieveSubscriptions(customerId: String): Pagination<Subscription>
    {
        val retrieveSubscriptionsCall = subscriptionService
                .retrieveSubscriptions(customerId)
                .execute()

        return retrieveSubscriptionsCall?.body()
               ?: throw EntityNotFoundException(RetrofitUtils.extractError(retrieveSubscriptionsCall))
    }

    private fun patchSubscription(customerId: String, subscription: Subscription): Subscription
    {
        val patchSubscriptionCall = subscriptionService
                .patchSubscription(customerId, subscription.id, subscription)
                .execute()

        return patchSubscriptionCall?.body()
               ?: throw InvalidActionException(RetrofitUtils.extractError(patchSubscriptionCall))
    }

    @Throws(InvalidActionException::class, EntityNotFoundException::class)
    fun suspendSubscription(customerId: String, subscriptionId: String): Subscription
    {
        val subscription = retrieveSubscription(customerId, subscriptionId)
        subscription.status = Subscription.SubscriptionStatus.suspended
        return patchSubscription(customerId, subscription)
    }

    @Throws(InvalidActionException::class)
    fun activateSubscription(customerId: String, subscriptionId: String): Subscription
    {
        val subscription = retrieveSubscription(customerId, subscriptionId)
        subscription.status = Subscription.SubscriptionStatus.active
        return patchSubscription(customerId, subscription)
    }

    @Throws(InvalidActionException::class)
    fun updateSubscriptionQuantity(customerId: String, subscriptionId: String, quantity: Int): Subscription
    {
        val subscription = retrieveSubscription(customerId, subscriptionId)
        subscription.quantity = quantity
        return patchSubscription(customerId, subscription)
    }

    @Throws(InvalidActionException::class)
    fun upgradeTrialToPaid(customerId: String, subscriptionId: String, targetOfferId: String)
    {
        val subscription = retrieveSubscription(customerId, subscriptionId)

        val conversion = Conversion(subscriptionId, subscription.offerId!!, targetOfferId, subscription.orderId!!, 1, "Monthly")

        val upgradeTrialToPaidCall = subscriptionService
                .upgradeTrialToPaid(customerId, subscriptionId, conversion)
                .execute()

        val conversionResult = upgradeTrialToPaidCall?.body()
                ?: throw EntityNotFoundException(RetrofitUtils.extractError(upgradeTrialToPaidCall))

        if(conversionResult.error != null) throw InvalidActionException(conversionResult.error.description)
    }
}
