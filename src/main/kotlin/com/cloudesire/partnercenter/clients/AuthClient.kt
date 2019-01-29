package com.cloudesire.partnercenter.clients

import com.cloudesire.partnercenter.exceptions.UnauthorizedException
import com.cloudesire.partnercenter.services.AuthService
import com.cloudesire.partnercenter.services.ServiceGenerator

class AuthClient
(
        private val clientId: String,
        private val clientSecret: String,
        private val resellerDomain: String
)
{
    private val authorityUrl: String = "https://login.windows.net"
    private val partnerCenterRootUrl: String = "https://api.partnercenter.microsoft.com"

    @Throws(UnauthorizedException::class)
    fun getAccessToken(): String?
    {
        return getPartnerCenterToken(getADToken())
    }

    private fun getADToken(): String?
    {
        val authService = ServiceGenerator()
                .changeApiBaseUrl(authorityUrl)
                .createService(AuthService::class.java)

        val adTokenCall = authService.getADToken(
                resellerDomain,
                clientId,
                clientSecret
        ).execute()

        if (adTokenCall.isSuccessful)
        {
            return adTokenCall.body()?.access_token
        } else
        {
            throw UnauthorizedException(adTokenCall.message())
        }
    }

    private fun getPartnerCenterToken(adToken: String?): String?
    {
        val authService = ServiceGenerator(adToken)
                .changeApiBaseUrl(partnerCenterRootUrl)
                .createService(AuthService::class.java)

        val partnerCenterTokenCall = authService
                .getPartnerCenterToken()
                .execute()

        if (partnerCenterTokenCall.isSuccessful)
        {
            return partnerCenterTokenCall.body()?.access_token
        } else
        {
            throw UnauthorizedException(partnerCenterTokenCall.message())
        }
    }
}
