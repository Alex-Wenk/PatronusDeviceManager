package com.patronus.devicemanager.device.web

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.patronus.devicemanager.PatronusDeviceManagerApplication
import com.patronus.devicemanager.device.data.DeviceRepository
import com.patronus.devicemanager.device.data.getDeviceEntitySample
import com.patronus.devicemanager.device.web.request.getCreateDeviceRequestTestSample
import com.patronus.devicemanager.device.web.response.GetDeviceResponse
import com.patronus.devicemanager.user.data.UserRepository
import com.patronus.devicemanager.user.data.getUserEntitySample
import com.patronus.devicemanager.web.exception.ErrorResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import java.util.*

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [PatronusDeviceManagerApplication::class])
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DeviceControllerIntegrationTest(
        @Autowired
        val restTemplate: TestRestTemplate,
        @Autowired
        val userRepository: UserRepository,
        @Autowired
        val deviceRepository: DeviceRepository
) {

    @Test
    fun `when getDevices is Called Then Devices are returned`() {
        val user = getUserEntitySample()
        val deviceNoUser = getDeviceEntitySample()
        val deviceWithUser = getDeviceEntitySample(user)

        deviceRepository.saveAll(listOf(deviceNoUser, deviceWithUser))

        val result = restTemplate.getForEntity("/device", String::class.java)
        val jacksonObjectMapper = jacksonObjectMapper()
        jacksonObjectMapper.registerModule(JavaTimeModule())
        val getDeviceResponses = jacksonObjectMapper.readValue(result.body, object : TypeReference<List<GetDeviceResponse>>() {})

        assertEquals(2, getDeviceResponses.size)
        Assertions.assertTrue(getDeviceResponses.find { device ->
            device.id == deviceNoUser.id &&
                    device.model == deviceNoUser.model &&
                    device.serialNumber == deviceNoUser.serialNumber &&
                    device.phoneNumber == deviceNoUser.phoneNumber
            device.userId == null
        } != null)
        Assertions.assertTrue(getDeviceResponses.find { device ->
            device.id == deviceWithUser.id &&
                    device.userId == user.id
        } != null)

    }

    @Test
    fun `when registerDevice is Called then the device is registered`() {
        val createDeviceRequest = getCreateDeviceRequestTestSample()

        val result = restTemplate.postForEntity("/device", createDeviceRequest, GetDeviceResponse::class.java)

        assertNotNull(result.body)
        assertEquals(createDeviceRequest.id, result.body?.id)
        assertEquals(createDeviceRequest.model, result.body?.model)
        assertEquals(createDeviceRequest.phoneNumber, result.body?.phoneNumber)
        assertEquals(createDeviceRequest.serialNumber, result.body?.serialNumber)

        val savedDevice = deviceRepository.findByIdOrNull(createDeviceRequest.id)

        assertNotNull(savedDevice)
        assertEquals(createDeviceRequest.id, savedDevice?.id)
        assertEquals(createDeviceRequest.model, savedDevice?.model)
        assertEquals(createDeviceRequest.phoneNumber, savedDevice?.phoneNumber)
        assertEquals(createDeviceRequest.serialNumber, savedDevice?.serialNumber)
    }

    @Test
    fun `when registerDevice is Called twice with same id then conflict error is thrown`() {
        val createDeviceRequest = getCreateDeviceRequestTestSample()

        val result1 = restTemplate.postForEntity("/device", createDeviceRequest, GetDeviceResponse::class.java)
        val result2 = restTemplate.postForEntity("/device", createDeviceRequest, ErrorResponse::class.java)

        assertEquals(HttpStatus.CONFLICT, result2.statusCode)
    }

    @Test
    fun `when assignDevice is called for an unregistered device id then 404 returned`() {
        val userEntity = getUserEntitySample()
        userRepository.save(userEntity)

        val notFoundUuid = UUID.randomUUID()

        val result = restTemplate.postForEntity("/device/$notFoundUuid/${userEntity.id}", null, String::class.java)

        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun `when assignDevice is called against an unregistered user id then 404 returned`() {
        val deviceEntity = getDeviceEntitySample()
        deviceRepository.save(deviceEntity)

        val notFoundUuid = UUID.randomUUID()

        val result = restTemplate.postForEntity("/device/${deviceEntity.id}/$notFoundUuid", null, String::class.java)

        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    @Test
    fun `when assignDevice is called for an registered device and user then 200 returned and device assigned`() {
        val deviceEntity = getDeviceEntitySample()
        deviceRepository.save(deviceEntity)
        val userEntity = getUserEntitySample()
        userRepository.save(userEntity)

        val result = restTemplate.postForEntity("/device/${deviceEntity.id}/${userEntity.id}", null, String::class.java)

        assertEquals(HttpStatus.OK, result.statusCode)

        assertEquals(userEntity.id, deviceRepository.findByIdOrNull(deviceEntity.id)?.userEntity?.id)
    }

    @Test
    fun `when assignDevice is called for an already assigned device, it is reassigned`() {
        val deviceEntity = getDeviceEntitySample()
        val userEntity1 = getUserEntitySample()
        userEntity1.devices = listOf(deviceEntity)
        val userEntity2 = getUserEntitySample()
        userRepository.saveAll(listOf(userEntity1, userEntity2))

        val result = restTemplate.postForEntity("/device/${deviceEntity.id}/${userEntity2.id}", null, String::class.java)

        assertEquals(HttpStatus.OK, result.statusCode)

        assertEquals(userEntity2.id, deviceRepository.findByIdOrNull(deviceEntity.id)?.userEntity?.id)
    }

}