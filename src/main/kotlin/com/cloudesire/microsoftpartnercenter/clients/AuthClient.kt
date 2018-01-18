package com.cloudesire.microsoftpartnercenter.clients

import com.cloudesire.microsoftpartnercenter.exceptions.UnauthorizedException
import com.cloudesire.microsoftpartnercenter.services.AuthService
import com.cloudesire.microsoftpartnercenter.services.ServiceGenerator

class AuthClient
(
        private val username: String,
        private val password: String,
        private val clientId: String,
        private val resellerDomain: String
)
{
    private val authorityUrl: String = "https://login.windows.net"
    private val partnerCenterRootUrl: String = "https://api.partnercenter.microsoft.com"

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
                "$username@$resellerDomain",
                password
        ).execute()

        if (adTokenCall.isSuccessful)
        {
            return adTokenCall.body()?.access_token
        }
        else
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
        }
        else
        {
            throw UnauthorizedException(partnerCenterTokenCall.message())
        }
    }
}
