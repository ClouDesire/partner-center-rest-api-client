package com.cloudesire.microsoftpartnercenter.clients

import com.cloudesire.microsoftpartnercenter.entities.Customer
import com.cloudesire.microsoftpartnercenter.exceptions.InvalidActionException
import com.cloudesire.microsoftpartnercenter.services.CustomerService
import com.cloudesire.microsoftpartnercenter.utils.RetrofitErrorConverter

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
