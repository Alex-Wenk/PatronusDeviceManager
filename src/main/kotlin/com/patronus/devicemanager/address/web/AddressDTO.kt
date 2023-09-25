package com.patronus.devicemanager.address.web

data class AddressDTO(
        val addressLine1: String,
        val addressLine2: String?,
        val postalCode: String,
        val city: String,
        val zoneCode: String?,
        val country: String
)
