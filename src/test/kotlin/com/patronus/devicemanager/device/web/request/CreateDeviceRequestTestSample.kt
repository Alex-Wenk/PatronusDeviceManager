package com.patronus.devicemanager.device.web.request

import java.util.*


fun getCreateDeviceRequestTestSample(): CreateDeviceRequest {
    return CreateDeviceRequest(
            UUID.randomUUID(),
            "serial number",
            "model",
            "+447572249537"
    )
}
