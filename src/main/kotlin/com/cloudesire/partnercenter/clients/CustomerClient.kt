package com.cloudesire.partnercenter.clients

import com.cloudesire.partnercenter.entities.Customer
import com.cloudesire.partnercenter.exceptions.EntityNotFoundException
import com.cloudesire.partnercenter.exceptions.InvalidActionException
import com.cloudesire.partnercenter.services.CustomerService
import com.cloudesire.partnercenter.utils.RetrofitErrorConverter

class CustomerClient
(
        private val customerService: CustomerService
)
{
    fun createCustomer(customer: Customer): Customer
    {
        val createCustomerCall = customerService
                .createCustomer(customer)
                .execute()

        return createCustomerCall?.body()
               ?: throw InvalidActionException(RetrofitErrorConverter.toString(createCustomerCall.errorBody()))
    }

    fun retrieveCustomer(customerid: String): Customer
    {
        val retrieveCustomerCall = customerService
                .retrieveCustomer(customerid)
                .execute()

        return retrieveCustomerCall?.body()
               ?: throw EntityNotFoundException(RetrofitErrorConverter.toString(retrieveCustomerCall.errorBody()))
    }

    fun deleteCustomer(customerId: String)
    {
        val deleteCustomerCall = customerService
                .deleteCustomer(customerId)
                .execute()

        if (!deleteCustomerCall.isSuccessful)
        {
            throw InvalidActionException(RetrofitErrorConverter.toString(deleteCustomerCall.errorBody()))
        }
    }
}
