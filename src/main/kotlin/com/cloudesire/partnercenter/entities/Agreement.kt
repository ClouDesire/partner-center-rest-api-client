package com.cloudesire.partnercenter.entities

data class Agreement
(
        val primaryContact: Contact,

        val dateAgreed: String,

        val templateId: String,

        val type: String = "MicrosoftCloudAgreement",

        val agreementType: String? = null // just for the GET
)
