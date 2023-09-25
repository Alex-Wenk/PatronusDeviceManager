package com.patronus.devicemanager.device.web.request

import java.util.*

data class CreateDeviceRequest(
        val id: UUID,
        val serialNumber: String,
        val model: String,
        val phoneNumber: String,
)