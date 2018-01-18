package com.cloudesire.partnercenter.utils

import com.cloudesire.partnercenter.entities.ErrorResponse
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody

class RetrofitErrorConverter
{
    companion object
    {
        private val moshiAdapter = Moshi
                .Builder()
                .build()
                .adapter(ErrorResponse::class.java)

        @JvmStatic
        fun toString(response: ResponseBody?): String
        {
            return moshiAdapter.fromJson(response?.source())?.description ?: "No reason"
        }
    }
}
