package com.patronus.devicemanager.device.web

import com.patronus.devicemanager.device.DeviceService
import com.patronus.devicemanager.device.converter.DeviceConverter
import com.patronus.devicemanager.device.web.request.CreateDeviceRequest
import com.patronus.devicemanager.device.web.response.GetDeviceResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController()
@RequestMapping("/device")
class DeviceController(
        private val deviceConverter: DeviceConverter,
        private val deviceService: DeviceService
) {

    @GetMapping
    fun getDevices(): List<GetDeviceResponse> {
        return deviceService.getDevices()
                .map { deviceEntity -> deviceConverter.entityToGetDeviceResponse(deviceEntity) }
    }

    @PostMapping()
    fun registerDevice(@RequestBody request: CreateDeviceRequest): GetDeviceResponse {
        return deviceConverter.entityToGetDeviceResponse(
                deviceService.registerDevice(
                        deviceConverter.createDeviceRequestToEntity(request)
                )
        )
    }

    @PostMapping("/{deviceId}/{userId}")
    fun assignDeviceToUser(@PathVariable deviceId: UUID, @PathVariable userId: UUID) {
        deviceService.assignDeviceToUser(deviceId, userId)
    }
}