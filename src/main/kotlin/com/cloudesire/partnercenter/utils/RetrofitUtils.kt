package com.cloudesire.partnercenter.utils

import com.cloudesire.partnercenter.entities.ErrorResponse
import com.squareup.moshi.Moshi
import retrofit2.Response
import java.io.EOFException

class RetrofitUtils
{
    companion object
    {
        private val moshiAdapter = Moshi
                .Builder()
                .build()
                .adapter(ErrorResponse::class.java)

        @JvmStatic
        fun extractError(response: Response<*>): String
        {
            return try
            {
                moshiAdapter.fromJson(response.errorBody()?.source())?.description ?: "No reason"
            }
            catch(e: EOFException)
            {
                response.message() ?: "No reason"
            }
        }
    }
}
