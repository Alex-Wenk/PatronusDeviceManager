package com.patronus.devicemanager.address.converter

import com.patronus.devicemanager.address.data.AddressEntity
import com.patronus.devicemanager.address.web.AddressDTO
import org.springframework.stereotype.Component
import java.util.*

@Component
class AddressConverter {

    fun fromEntityToDTO(addressEntity: AddressEntity): AddressDTO {
        return AddressDTO(
                addressEntity.addressLine1,
                addressEntity.addressLine2,
                addressEntity.postalCode,
                addressEntity.city,
                addressEntity.zoneCode,
                addressEntity.country
        )
    }

    fun fromDtoToEntity(dto: AddressDTO): AddressEntity {
        return AddressEntity(
                UUID.randomUUID(),
                dto.addressLine1,
                dto.addressLine2,
                dto.postalCode,
                dto.city,
                dto.zoneCode,
                dto.country
        )
    }
}