package com.patronus.devicemanager.device

import com.patronus.devicemanager.device.data.DeviceEntity
import com.patronus.devicemanager.device.data.DeviceRepository
import com.patronus.devicemanager.user.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeviceService(
        private val deviceRepository: DeviceRepository,
        private val userService: UserService
) {

    fun getDevices(): List<DeviceEntity> {
        return deviceRepository.findAll()
    }

    fun registerDevice(deviceEntity: DeviceEntity): DeviceEntity {
        verifyDeviceNotRegistered(deviceEntity.id)
        return deviceRepository.save(deviceEntity)
    }

    fun verifyDeviceNotRegistered(deviceId: UUID) {
        deviceRepository.findByIdOrNull(deviceId)?.let {
            throw IllegalArgumentException("A device with id $deviceId is already registered.")
        }
    }

    fun assignDeviceToUser(deviceId: UUID, userId: UUID) {
        val device = deviceRepository.findByIdOrNull(deviceId)
                ?: throw IllegalArgumentException("Device with UUID $deviceId not found.")
        device.userEntity = userService.getUser(userId)
        deviceRepository.save(device)
    }


}