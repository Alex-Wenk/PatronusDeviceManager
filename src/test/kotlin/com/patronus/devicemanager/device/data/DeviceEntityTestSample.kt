package com.patronus.devicemanager.device.data

import com.patronus.devicemanager.user.data.UserEntity
import java.util.*

fun getDeviceEntitySample(userEntity: UserEntity? = null): DeviceEntity {
    return DeviceEntity(
            UUID.randomUUID(),
            "serialNumber",
            "model",
            "phoneNumber",
            userEntity
    )
}


