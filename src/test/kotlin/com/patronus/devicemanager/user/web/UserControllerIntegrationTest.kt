package com.patronus.devicemanager.user.web

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.patronus.devicemanager.PatronusDeviceManagerApplication
import com.patronus.devicemanager.address.web.getAddressDtoTestSample
import com.patronus.devicemanager.device.data.getDeviceEntitySample
import com.patronus.devicemanager.user.data.UserEntity
import com.patronus.devicemanager.user.data.UserRepository
import com.patronus.devicemanager.user.data.getUserEntitySample
import com.patronus.devicemanager.user.web.request.CreateUserRequest
import com.patronus.devicemanager.user.web.response.GetUserResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDate


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [PatronusDeviceManagerApplication::class])
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerIntegrationTest (
        @Autowired
        val restTemplate: TestRestTemplate,
        @Autowired
        val userRepository: UserRepository
){

    @Test
    fun `when CreateUser Called Then User is Created`() {
        val request = CreateUserRequest("firstname", "lastname",
                getAddressDtoTestSample(), LocalDate.of(1995, 11, 10))

        val result = restTemplate.postForEntity("/user", request, GetUserResponse::class.java)

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)

        assertEquals(request.address, result.body?.address)
        assertEquals(request.birthday, result.body?.birthday)
        assertEquals(request.firstName, result.body?.firstName)
        assertEquals(request.lastName, result.body?.lastName)

        val savedUser: UserEntity? = userRepository.findByIdOrNull(result.body?.id)
        assertNotNull(savedUser)
        assertEquals(request.firstName, savedUser?.firstName)
        assertEquals(request.lastName, savedUser?.lastName)
        assertEquals(request.birthday, savedUser?.birthday)
        assertEquals(request.address.addressLine1, savedUser?.addressEntity?.addressLine1)
        assertEquals(request.address.addressLine2, savedUser?.addressEntity?.addressLine2)
        assertEquals(request.address.city, savedUser?.addressEntity?.city)
        assertEquals(request.address.country, savedUser?.addressEntity?.country)
        assertEquals(request.address.postalCode, savedUser?.addressEntity?.postalCode)
        assertEquals(request.address.zoneCode, savedUser?.addressEntity?.zoneCode)
    }

    @Test
    fun `when GetUser is Called with no query params then all Users are returned`() {
        // setup
        val firstUser = getUserEntitySample()
        val secondUser = getUserEntitySample()
        val device = getDeviceEntitySample(secondUser)
        secondUser.devices = listOf(device)

        userRepository.saveAll(listOf(firstUser, secondUser))

        // request and deserialize
        // TODO: find a better way to deserialize to list of objects from resttemplate
        val result = restTemplate.getForEntity("/user", String::class.java)
        val jacksonObjectMapper = jacksonObjectMapper()
        jacksonObjectMapper.registerModule(JavaTimeModule())
        val getUserResponses = jacksonObjectMapper.readValue(result.body, object : TypeReference<List<GetUserResponse>>() {})

        // verify both  users returned
        assertEquals(2, getUserResponses.size)
        assertTrue(getUserResponses.find { user ->
            user.id == firstUser.id &&
                    user.devices.isEmpty()
        } != null)
        assertTrue(getUserResponses.find { user ->
            user.id == secondUser.id &&
                    user.devices.size == 1 &&
                    user.devices[0].id == device.id
        } != null)

    }

    @Test
    fun `when GetUser is Called with hasDevice=1 Then only users with devices are returned`() {
        // setup
        val firstUser = getUserEntitySample()
        val secondUser = getUserEntitySample()
        val device = getDeviceEntitySample(secondUser)
        secondUser.devices = listOf(device)

        userRepository.saveAll(listOf(firstUser, secondUser))

        // request and deserialize
        // TODO: find a better way to deserialize to list of objects from resttemplate
        val result = restTemplate.getForEntity("/user?hasDevice=1", String::class.java)
        val jacksonObjectMapper = jacksonObjectMapper()
        jacksonObjectMapper.registerModule(JavaTimeModule())
        val getUserResponses = jacksonObjectMapper.readValue(result.body, object : TypeReference<List<GetUserResponse>>() {})

        // verify both  users returned
        assertEquals(1, getUserResponses.size)
        val user = getUserResponses[0]
        assertTrue(
            user.id == secondUser.id &&
                    user.devices.size == 1 &&
                    user.devices[0].id == device.id)

    }

    @Test
    fun `when GetUser is Called with hasDevice=0 Then only users without devices are returned`() {
        // setup
        val firstUser = getUserEntitySample()
        val secondUser = getUserEntitySample()
        val device = getDeviceEntitySample(secondUser)
        secondUser.devices = listOf(device)

        userRepository.saveAll(listOf(firstUser, secondUser))

        // request and deserialize
        // TODO: find a better way to deserialize to list of objects from resttemplate
        val result = restTemplate.getForEntity("/user?hasDevice=0", String::class.java)
        val jacksonObjectMapper = jacksonObjectMapper()
        jacksonObjectMapper.registerModule(JavaTimeModule())
        val getUserResponses = jacksonObjectMapper.readValue(result.body, object : TypeReference<List<GetUserResponse>>() {})

        // verify both  users returned
        assertEquals(1, getUserResponses.size)
        val user = getUserResponses[0]
        assertTrue(
                user.id == firstUser.id &&
                        user.devices.isEmpty())

    }
}