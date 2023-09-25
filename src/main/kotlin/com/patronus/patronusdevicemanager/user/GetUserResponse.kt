package com.patronus.patronusdevicemanager.user

import com.patronus.patronusdevicemanager.address.AddressDTO
import com.patronus.patronusdevicemanager.device.GetDeviceResponse
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