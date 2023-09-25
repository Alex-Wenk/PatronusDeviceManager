package com.patronus.devicemanager.user.web.response

import com.patronus.devicemanager.address.web.AddressDTO
import com.patronus.devicemanager.device.web.response.GetDeviceResponse
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