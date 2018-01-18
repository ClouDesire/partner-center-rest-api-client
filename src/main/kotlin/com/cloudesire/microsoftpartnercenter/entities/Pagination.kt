package com.cloudesire.microsoftpartnercenter.entities

data class Pagination<out T>
(
        val totalCount: Long,

        val items: List<T>
)
