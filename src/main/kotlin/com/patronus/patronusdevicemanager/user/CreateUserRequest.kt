package com.patronus.patronusdevicemanager.user

import com.patronus.patronusdevicemanager.address.AddressDTO
import java.time.LocalDate

data class CreateUserRequest(
        val firstName: String,
        val lastName: String,
        val address: AddressDTO,
        val birthday: LocalDate,
)