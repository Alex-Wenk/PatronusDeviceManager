package com.patronus.devicemanager.device.converter

import com.patronus.devicemanager.device.data.getDeviceEntitySample
import com.patronus.devicemanager.device.web.request.CreateDeviceRequest
import com.patronus.devicemanager.user.data.getUserEntitySample
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class DeviceConverterTest (
        @InjectMockKs
        private val deviceConverter: DeviceConverter
){

    @Test
    fun `test createDeviceRequestToEntity`() {
        val createDeviceRequest = CreateDeviceRequest(
                UUID.randomUUID(),
                "123456",
                "Model X",
                "555-123-4567"
        )

        val deviceEntity = deviceConverter.createDeviceRequestToEntity(createDeviceRequest)

        assertEquals(createDeviceRequest.id, deviceEntity.id)
        assertEquals(createDeviceRequest.serialNumber, deviceEntity.serialNumber)
        assertEquals(createDeviceRequest.model, deviceEntity.model)
        assertEquals(createDeviceRequest.phoneNumber, deviceEntity.phoneNumber)
        assertNull(deviceEntity.userEntity)
    }

    @Test
    fun `test entityToGetDeviceResponse`() {
        val userEntity = getUserEntitySample()
        val deviceEntity = getDeviceEntitySample(userEntity)

        val getDeviceResponse = deviceConverter.entityToGetDeviceResponse(deviceEntity)

        assertEquals(deviceEntity.id, getDeviceResponse.id)
        assertEquals(deviceEntity.serialNumber, getDeviceResponse.serialNumber)
        assertEquals(deviceEntity.model, getDeviceResponse.model)
        assertEquals(deviceEntity.phoneNumber, getDeviceResponse.phoneNumber)
        assertEquals(userEntity.id, getDeviceResponse.userId)
    }
}
