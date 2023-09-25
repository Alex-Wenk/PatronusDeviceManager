package com.patronus.devicemanager.device

import java.util.*

data class GetDeviceResponse(
        val id: UUID,
        val serialNumber: String,
        val model: String,
        val phoneNumber: String,
        val userId: UUID?
)
