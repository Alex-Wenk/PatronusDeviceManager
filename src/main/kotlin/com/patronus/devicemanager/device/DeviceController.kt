package com.patronus.devicemanager.device

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/device")
class DeviceController {
    
    @GetMapping
    fun getDevices(): List<GetDeviceResponse> {
        return emptyList()
    }

    @PostMapping
    fun registerDevice(@RequestBody device: CreateDeviceRequest): GetDeviceResponse? {
        return null
    }

    @PostMapping("/{deviceId}/{userId}")
    fun assignDeviceToUser(
        @PathVariable deviceId: String,
        @PathVariable userId: String
    ){

    }
}