package com.patronus.devicemanager.user.converter

import com.patronus.devicemanager.address.converter.AddressConverter
import com.patronus.devicemanager.address.web.getAddressDtoTestSample
import com.patronus.devicemanager.device.converter.DeviceConverter
import com.patronus.devicemanager.device.data.getDeviceEntitySample
import com.patronus.devicemanager.user.data.getUserEntitySample
import com.patronus.devicemanager.user.web.request.CreateUserRequest
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
class UserConverterTest(
        @SpyK
        var addressConverter: AddressConverter,
        @SpyK
        var deviceConverter: DeviceConverter,
        @InjectMockKs
        var userConverter: UserConverter
) {
    @Test
    fun `createUserRequestToEntity should convert CreateUserRequest to UserEntity`() {
        val createUserRequest = CreateUserRequest(
                "firstName",
                "lastName",
                getAddressDtoTestSample(),
                LocalDate.of(1990, 5, 15)
        )

        val resultEntity = userConverter.createUserRequestToEntity(createUserRequest)

        assertEquals(createUserRequest.firstName, resultEntity.firstName)
        assertEquals(createUserRequest.lastName, resultEntity.lastName)
        assertEquals(createUserRequest.address, AddressConverter().fromEntityToDTO(resultEntity.addressEntity))
        assertEquals(createUserRequest.birthday, resultEntity.birthday)
        assertNotNull(resultEntity.id)
    }

    @Test
    fun `userEntityToGetUserResponse should convert UserEntity to GetUserResponse`() {
        val addressDTO = getAddressDtoTestSample()
        every { addressConverter.fromEntityToDTO(any()) }.returns(addressDTO)

        val userEntity = getUserEntitySample()
        val deviceEntity = getDeviceEntitySample(userEntity)
        userEntity.devices = listOf(deviceEntity)

        val resultResponse = userConverter.userEntityToGetUserResponse(userEntity)

        assertEquals(userEntity.id, resultResponse.id)
        assertEquals(userEntity.firstName, resultResponse.firstName)
        assertEquals(userEntity.lastName, resultResponse.lastName)
        assertEquals(addressDTO, resultResponse.address)
        assertEquals(userEntity.birthday, resultResponse.birthday)
        assertEquals(1, resultResponse.devices.size)
        assertEquals(deviceEntity.id, resultResponse.devices[0].id)
        assertEquals(deviceEntity.serialNumber, resultResponse.devices[0].serialNumber)
        assertEquals(deviceEntity.model, resultResponse.devices[0].model)
        assertEquals(deviceEntity.phoneNumber, resultResponse.devices[0].phoneNumber)
        assertEquals(deviceEntity.userEntity?.id, resultResponse.devices[0].userId)
    }
}
