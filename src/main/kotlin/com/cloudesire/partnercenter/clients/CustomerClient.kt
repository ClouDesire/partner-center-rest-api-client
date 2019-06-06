package com.cloudesire.partnercenter.clients

import com.cloudesire.partnercenter.entities.Agreement
import com.cloudesire.partnercenter.entities.Contact
import com.cloudesire.partnercenter.entities.Customer
import com.cloudesire.partnercenter.entities.Pagination
import com.cloudesire.partnercenter.exceptions.EntityNotFoundException
import com.cloudesire.partnercenter.exceptions.InvalidActionException
import com.cloudesire.partnercenter.services.CustomerService
import com.cloudesire.partnercenter.utils.RetrofitUtils
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_INSTANT
import java.util.*

class CustomerClient
(
        private val customerService: CustomerService
)
{
    @Throws(InvalidActionException::class)
    fun createCustomer(customer: Customer): Customer
    {
        val createCustomerCall = customerService
                .createCustomer(customer)
                .execute()

        return createCustomerCall?.body()
               ?: throw InvalidActionException(RetrofitUtils.extractError(createCustomerCall))
    }

    @Throws(InvalidActionException::class)
    fun retrieveAgreements(): Pagination<Agreement>
    {
        val retrieveAgreementsCall = customerService
                .retrieveAgreements()
                .execute()

        return retrieveAgreementsCall?.body()
                ?: throw InvalidActionException(RetrofitUtils.extractError(retrieveAgreementsCall))
    }

    @Throws(InvalidActionException::class)
    fun acceptAgreement(customer: Customer, agreementId: String, firstName: String, lastName: String): Agreement
    {
        val agreement = Agreement(
                Contact(firstName, lastName, customer.billingProfile.email),
                dateAgreed = ZonedDateTime.now().minusMinutes(1).format(ISO_INSTANT).toString(),
                templateId = agreementId
        )

        val acceptCloudAgreementCall = customerService
                .acceptAgreement(customer.id!!, agreement)
                .execute()

        return acceptCloudAgreementCall?.body()
                ?: throw InvalidActionException(RetrofitUtils.extractError(acceptCloudAgreementCall))
    }

    @Throws(EntityNotFoundException::class)
    fun retrieveCustomer(customerid: String): Customer
    {
        val retrieveCustomerCall = customerService
                .retrieveCustomer(customerid)
                .execute()

        return retrieveCustomerCall?.body()
               ?: throw EntityNotFoundException(RetrofitUtils.extractError(retrieveCustomerCall))
    }

    @Throws(InvalidActionException::class)
    fun deleteCustomer(customerId: String)
    {
        val deleteCustomerCall = customerService
                .deleteCustomer(customerId)
                .execute()

        if (!deleteCustomerCall.isSuccessful)
        {
            throw InvalidActionException(RetrofitUtils.extractError(deleteCustomerCall))
        }
    }
}
