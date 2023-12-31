package com.patronus.devicemanager.user.web.request

import com.patronus.devicemanager.address.web.AddressDTO
import java.time.LocalDate

data class CreateUserRequest(
        val firstName: String,
        val lastName: String,
        val address: AddressDTO,
        val birthday: LocalDate,
)