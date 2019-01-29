package com.cloudesire.partnercenter

import com.cloudesire.partnercenter.entities.*
import com.winterbe.expekt.should
import mu.KLogging
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.util.*

class WorkflowTest
{
    private val office365OfferId: String = "031C9E47-4802-4248-838E-778FB1D2CC05"
    private val office365TrialOfferId: String = "C0BD2E08-11AC-4836-BDC7-3712E744922F"

    private var client: MicrosoftPartnerCenterClient? = null

    companion object: KLogging()

    @Before
    fun refreshToken()
    {
        client?.let { client!!.refreshToken() }
    }

    @Test
    fun workflowTest()
    {
        var customer: Customer? = null
        var order: Order? = null

        client = MicrosoftPartnerCenterClient.Builder()
                .clientId("1bca3e1f-8123-4ebf-8b2e-c97b6840a06a")
                .clientSecret("%7C%5D%24o%5B%5D%5DJ%3A%2B3%28%7B%3B-%5E%3D%26%7DVYL%5B%5D%26%24%3A%3A%5D%40.%3D%3B%40_%21%26L-%28%3Ed%29%2B%7C%25%26")
                .resellerDomain("TestTestCloudesiretip.onmicrosoft.com")
                .build()

        client.should.be.not.`null`

        try
        {
            customer = createCustomer()
            customer.should.not.be.`null`
            customer!!.id.should.not.be.empty

//            order = createTrialOrder(customer.id!!, customer.billingProfile.defaultAddress.country!!)
//            order.should.not.be.`null`
//            order.id.should.not.be.empty
//            order.referenceCustomerId.should.be.equal(customer.id)
//            order.lineItems[0].offerId.should.be.equal(office365TrialOfferId)
//            order.lineItems[0].quantity.should.be.equal(25)
//
//            val subscriptions = client
//                    ?.getSubscriptionClient()
//                    ?.retrieveSubscriptionsByOrderId(customer.id!!, order.id!!)
//            subscriptions!!.totalCount.should.be.equal(1)
//
//            order = upgradeTrialToPaid(customer.id!!, subscriptions.items[0].id, order.id!!)
//            order.lineItems[0].offerId.should.be.equal(office365OfferId)
//            order.lineItems[0].quantity.should.be.equal(1)
//
//
//            suspendActivateAndUpdateSubscription(customer.id!!, subscriptions.items[0].id)
        }
        finally
        {
            cleanUp(customer, order)
        }
    }

    private fun cleanUp(customer: Customer?, order: Order?)
    {
        try
        {
            if (customer?.id != null)
            {
                if (order?.id != null)
                {
                    client!!
                            .getSubscriptionClient()
                            .suspendSubscription(customerId = customer.id!!, subscriptionId = order.lineItems[0].subscriptionId!!)
                }

                client!!.getCustomerClient().deleteCustomer(customer.id!!)
            }
        }
        catch (e: Exception)
        {
            KLogging().logger.error { "Cannot clean up the resources: ${e.message}" }
        }
    }

    private fun createCustomer(): Customer?
    {
        val randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 6)
        val companyProfile = CompanyProfile("cloudesire$randomString.onmicrosoft.com", "cloudesire")
        val address = Address("Via Umberto Forti, 6", null, "Pisa", "PI", "56121", "IT", null, "Beppe", "Rossi", "123")
        val billingProfile = BillingProfile(email = "dev@cloudesire.com", companyName = "cloudesire", defaultAddress = address)
        val customer = Customer(null, companyProfile, billingProfile)
        return client!!.getCustomerClient().createCustomer(customer)
    }

    private fun createTrialOrder(customerId: String, customerCountryCode: String): Order
    {
        val offer = client!!.getOfferClient().retrieveOffer(office365TrialOfferId, customerCountryCode)
        val orderLine = OrderLine(offerId = office365TrialOfferId, quantity = offer.minimumQuantity)
        val order = Order(referenceCustomerId = customerId, lineItems = arrayListOf(orderLine), billingCycle = "None")
        return client!!.getOrderClient().createOrder(customerId = customerId, order = order)
    }

    private fun upgradeTrialToPaid(customerId: String, subscriptionId: String, orderId: String): Order
    {
        client!!.getSubscriptionClient().upgradeTrialToPaid(customerId, subscriptionId, office365OfferId)
        return client!!.getOrderClient().retrieveOrder(customerId = customerId, orderId = orderId)
    }

    private fun suspendActivateAndUpdateSubscription(customerId: String, subscriptionId: String)
    {
        var subscription: Subscription = client!!.getSubscriptionClient().suspendSubscription(customerId = customerId, subscriptionId = subscriptionId)
        subscription.status.should.be.equal(Subscription.SubscriptionStatus.suspended)
        subscription = client!!.getSubscriptionClient().activateSubscription(customerId = customerId, subscriptionId = subscriptionId)
        subscription.status.should.be.equal(Subscription.SubscriptionStatus.active)
        subscription = client!!.getSubscriptionClient().updateSubscriptionQuantity(customerId = customerId, subscriptionId = subscriptionId, quantity = 2)
        subscription.quantity.should.be.equal(2)
    }
}
