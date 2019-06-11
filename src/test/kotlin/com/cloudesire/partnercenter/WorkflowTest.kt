package com.cloudesire.partnercenter

import com.cloudesire.partnercenter.entities.*
import com.winterbe.expekt.should
import mu.KLogging
import org.junit.Before
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
                .clientId(getEnvironmentVariable("PARTNER_CENTER_CLIENT_ID"))
                .username(getEnvironmentVariable("PARTNER_CENTER_USERNAME"))
                .password(getEnvironmentVariable("PARTNER_CENTER_PASSWORD"))
                .resellerDomain(getEnvironmentVariable("PARTNER_CENTER_DOMAIN"))
                .build()

        client.should.be.not.`null`

        try
        {
            customer = createCustomer()
            customer.should.not.be.`null`
            customer!!.id.should.not.be.empty

            val microsoftCloudAgreement = retrieveMicrosoftCloudAgreementId()
            acceptAgreement(customer, microsoftCloudAgreement, "Andrea", "Dipré")

            val agreements = client!!.getCustomerClient().retrieveAgreementsByCustomer(customer).items

            agreements.should.size(1)
            agreements[0].templateId.should.equal(microsoftCloudAgreement)

            order = createTrialOrder(customer.id!!, customer.billingProfile.defaultAddress.country!!)
            order.should.not.be.`null`
            order.id.should.not.be.empty
            order.referenceCustomerId.should.be.equal(customer.id)
            order.lineItems[0].offerId.should.be.equal(office365TrialOfferId)
            order.lineItems[0].quantity.should.be.equal(25)

            val subscriptions = client
                    ?.getSubscriptionClient()
                    ?.retrieveSubscriptionsByOrderId(customer.id!!, order.id!!)
            subscriptions!!.totalCount.should.be.equal(1)

            order = upgradeTrialToPaid(customer.id!!, subscriptions.items[0].id, order.id!!)
            order.lineItems[0].offerId.should.be.equal(office365OfferId)
            order.lineItems[0].quantity.should.be.equal(1)


            suspendActivateAndUpdateSubscription(customer.id!!, subscriptions.items[0].id)
        }
        finally
        {
            cleanUp(customer, order)
        }
    }

    private fun retrieveMicrosoftCloudAgreementId(): String
    {
        return client!!.getCustomerClient().retrieveAgreements().items.findLast {
            agreement -> agreement.agreementType == "MicrosoftCloudAgreement"
        }!!.templateId
    }

    private fun acceptAgreement(customer: Customer, agreementId: String, firstName: String, lastName: String)
    {
        client!!.getCustomerClient().acceptAgreement(customer, agreementId, firstName, lastName)
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

    private fun getEnvironmentVariable(envVar: String): String
    {
        return System.getenv(envVar)
    }
}
