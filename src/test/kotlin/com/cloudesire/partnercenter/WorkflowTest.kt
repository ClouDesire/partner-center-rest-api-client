package com.cloudesire.partnercenter

import com.cloudesire.partnercenter.entities.*
import com.winterbe.expekt.should
import org.junit.Ignore
import org.junit.Test
import java.util.*

class WorkflowTest
{
    private val office365OfferId: String = "031C9E47-4802-4248-838E-778FB1D2CC05"
    private val office365TrialOfferId: String = "C0BD2E08-11AC-4836-BDC7-3712E744922F"

    @Test
    @Ignore
    fun completeWorkflowTest()
    {
        val client = MicrosoftPartnerCenterClient.Builder()
                .clientId("")
                .username("")
                .password("")
                .resellerDomain("")
                .build()

        // create customer
        val randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 6)
        val companyProfile = CompanyProfile("cloudesire$randomString.onmicrosoft.com", "cloudesire")
        val address = Address("Via Umberto Forti, 6", null, "Pisa", "PI", "56121", "IT", null, "Beppe", "Rossi", "123")
        val billingProfile = BillingProfile(email = "dev@cloudesire.com", companyName = "cloudesire", defaultAddress = address)
        var customer = Customer(null, companyProfile, billingProfile)
        customer = client.getCustomerClient().createCustomer(customer)
        customer.id.should.not.be.empty

        // create order
        val orderLine = OrderLine(offerId = office365TrialOfferId, quantity = 25)
        var order = Order(referenceCustomerId = customer.id!!, lineItems = arrayListOf(orderLine), billingCycle = "None")
        order = client.getOrderClient().createOrder(customerId = customer.id!!, order = order)
        order.id.should.not.be.empty
        order.referenceCustomerId.should.be.equal(customer.id)
        order.lineItems[0].offerId.should.be.equal(office365TrialOfferId)
        order.lineItems[0].quantity.should.be.equal(25)

        var allSubscriptions = client.getSubscriptionClient().retrieveSubscriptions(customer.id!!)
        allSubscriptions.totalCount.should.be.equal(1)

        // retrieve order
        order = client.getOrderClient().retrieveOrder(customerId = customer.id!!, orderId = order.id!!)
        order.id.should.not.be.empty

        // upgrade to normal order
        client.getSubscriptionClient().upgradeTrialToNormal(customerId = customer.id!!, subscriptionId = allSubscriptions.items[0].id, targetOfferId = office365OfferId)
        allSubscriptions = client.getSubscriptionClient().retrieveSubscriptions(customer.id!!)
        order = client.getOrderClient().retrieveOrder(customerId = customer.id!!, orderId = allSubscriptions.items[0].orderId!!)
        order.lineItems[0].offerId.should.be.equal(office365OfferId)
        order.lineItems[0].quantity.should.be.equal(1)

        // suspend, reactivate and update subscription quantity
        var subscription: Subscription
        subscription = client.getSubscriptionClient().suspendSubscription(customerId = customer.id!!, subscriptionId = order.lineItems[0].subscriptionId!!)
        subscription.status.should.be.equal(Subscription.SubscriptionStatus.suspended)
        subscription = client.getSubscriptionClient().activateSubscription(customerId = customer.id!!, subscriptionId = order.lineItems[0].subscriptionId!!)
        subscription.status.should.be.equal(Subscription.SubscriptionStatus.active)
        subscription = client.getSubscriptionClient().updateSubscriptionQuantity(customerId = customer.id!!, subscriptionId = order.lineItems[0].subscriptionId!!, quantity = 2)
        subscription.quantity.should.be.equal(2)

        client.refreshToken()

        // clean up
        client.getSubscriptionClient().suspendSubscription(customerId = customer.id!!, subscriptionId = order.lineItems[0].subscriptionId!!)
        client.getCustomerClient().deleteCustomer(customer.id!!)
    }
}
