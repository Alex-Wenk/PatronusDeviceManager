package com.patronus.devicemanager.user.data

import com.patronus.devicemanager.address.data.getAddressEntitySample
import java.time.LocalDate
import java.util.*


fun getUserEntitySample(): UserEntity {
    return UserEntity(
            UUID.randomUUID(),
            "firstname",
            "lastname",
            getAddressEntitySample(),
            LocalDate.of(1995, 11, 10)
    )
}
