package com.cloudesire.microsoftpartnercenter.clients

import com.cloudesire.microsoftpartnercenter.entities.Order
import com.cloudesire.microsoftpartnercenter.exceptions.EntityNotFoundException
import com.cloudesire.microsoftpartnercenter.exceptions.InvalidActionException
import com.cloudesire.microsoftpartnercenter.services.OrderService
import com.cloudesire.microsoftpartnercenter.utils.RetrofitErrorConverter

class OrderClient
(
        private val orderService: OrderService
)
{
    fun createOrder(customerId: String, order: Order): Order
    {
        val createOrderCall = orderService
                .createOrder(customerId, order)
                .execute()

        return createOrderCall?.body()
               ?: throw InvalidActionException(RetrofitErrorConverter.toString(createOrderCall.errorBody()))
    }

    fun retrieveOrder(customerId: String, orderId: String): Order
    {
        val retrieveOrderCall = orderService
                .retrieveOrder(customerId, orderId)
                .execute()

        return retrieveOrderCall?.body()
            ?: throw EntityNotFoundException(RetrofitErrorConverter.toString(retrieveOrderCall.errorBody()))
    }
}
