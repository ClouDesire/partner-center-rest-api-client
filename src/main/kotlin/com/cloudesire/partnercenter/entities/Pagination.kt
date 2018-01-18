package com.cloudesire.partnercenter.entities

data class Pagination<out T>
(
        val totalCount: Long,

        val items: List<T>
)
