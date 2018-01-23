package com.cloudesire.partnercenter.clients

import com.cloudesire.partnercenter.entities.Customer
import com.cloudesire.partnercenter.exceptions.EntityNotFoundException
import com.cloudesire.partnercenter.exceptions.InvalidActionException
import com.cloudesire.partnercenter.services.CustomerService
import com.cloudesire.partnercenter.utils.RetrofitUtils

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
