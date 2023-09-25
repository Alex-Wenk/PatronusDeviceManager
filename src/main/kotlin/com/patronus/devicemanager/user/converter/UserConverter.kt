package com.patronus.devicemanager.user.converter

import com.patronus.devicemanager.address.converter.AddressConverter
import com.patronus.devicemanager.device.converter.DeviceConverter
import com.patronus.devicemanager.user.data.UserEntity
import com.patronus.devicemanager.user.web.request.CreateUserRequest
import com.patronus.devicemanager.user.web.response.GetUserResponse
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserConverter(
        private val addressConverter: AddressConverter,
        private val deviceConverter: DeviceConverter
) {

    fun createUserRequestToEntity(request: CreateUserRequest): UserEntity {
        return UserEntity(
                UUID.randomUUID(),
                request.firstName,
                request.lastName,
                addressConverter.fromDtoToEntity(request.address),
                request.birthday
        )
    }

    fun userEntityToGetUserResponse(entity: UserEntity): GetUserResponse {
        return GetUserResponse(
                entity.id,
                entity.firstName,
                entity.lastName,
                addressConverter.fromEntityToDTO(entity.addressEntity),
                entity.birthday,
                entity.devices?.map { device -> deviceConverter.entityToGetDeviceResponse(device) }
                        ?: emptyList()
        )
    }

}