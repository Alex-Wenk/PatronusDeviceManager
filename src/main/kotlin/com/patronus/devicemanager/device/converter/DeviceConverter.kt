package com.patronus.devicemanager.device.converter

import com.patronus.devicemanager.device.web.request.CreateDeviceRequest
import com.patronus.devicemanager.device.data.DeviceEntity
import com.patronus.devicemanager.device.web.response.GetDeviceResponse
import org.springframework.stereotype.Component

@Component
class DeviceConverter {

    fun createDeviceRequestToEntity(request: CreateDeviceRequest): DeviceEntity {
        return DeviceEntity(
                request.id,
                request.serialNumber,
                request.model,
                request.phoneNumber
        )
    }

    fun entityToGetDeviceResponse(deviceEntity: DeviceEntity): GetDeviceResponse {
        return GetDeviceResponse(
                deviceEntity.id,
                deviceEntity.serialNumber,
                deviceEntity.model,
                deviceEntity.phoneNumber,
                deviceEntity.userEntity?.id
        )
    }
}