package com.patronus.devicemanager.user

import com.patronus.devicemanager.address.web.AddressDTO
import com.patronus.devicemanager.device.GetDeviceResponse
import java.time.LocalDate
import java.util.*

data class GetUserResponse(
        val id: UUID,
        val firstName: String,
        val lastName: String,
        val address: AddressDTO,
        val birthday: LocalDate,
        var devices: List<GetDeviceResponse>
)