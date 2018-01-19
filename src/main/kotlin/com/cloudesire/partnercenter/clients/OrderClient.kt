package com.cloudesire.partnercenter.clients

import com.cloudesire.partnercenter.entities.Order
import com.cloudesire.partnercenter.exceptions.EntityNotFoundException
import com.cloudesire.partnercenter.exceptions.InvalidActionException
import com.cloudesire.partnercenter.services.OrderService
import com.cloudesire.partnercenter.utils.RetrofitErrorConverter

class OrderClient
(
        private val orderService: OrderService
)
{
    @Throws(InvalidActionException::class)
    fun createOrder(customerId: String, order: Order): Order
    {
        val createOrderCall = orderService
                .createOrder(customerId, order)
                .execute()

        return createOrderCall?.body()
               ?: throw InvalidActionException(RetrofitErrorConverter.toString(createOrderCall.errorBody()))
    }

    @Throws(EntityNotFoundException::class)
    fun retrieveOrder(customerId: String, orderId: String): Order
    {
        val retrieveOrderCall = orderService
                .retrieveOrder(customerId, orderId)
                .execute()

        return retrieveOrderCall?.body()
               ?: throw EntityNotFoundException(RetrofitErrorConverter.toString(retrieveOrderCall.errorBody()))
    }
}
