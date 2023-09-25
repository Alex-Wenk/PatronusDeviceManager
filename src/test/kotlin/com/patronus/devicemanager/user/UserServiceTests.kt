package com.patronus.devicemanager.user

import com.patronus.devicemanager.user.data.UserEntity
import com.patronus.devicemanager.user.data.UserRepository
import com.patronus.devicemanager.user.data.getUserEntitySample
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@ExtendWith(MockKExtension::class)
class UserServiceTests (
        @MockK
        private val userRepository: UserRepository,
        @InjectMockKs
        private  val userService: UserService
){

    @Test
    fun `getUsers should not filter on hasDevice if hasDevice is not set`() {
        mockkObject(UserRepository.Companion)
        mockkStatic(Specification::class)
        val baseSpecificationMock: Specification<UserEntity> = mockk()
        every { Specification.where<UserEntity>(null) }.returns(baseSpecificationMock)
        val hasDeviceSpecificationMock: Specification<UserEntity> = mockk()
        every { UserRepository.hasDevice(any()) }.returns(hasDeviceSpecificationMock)
        every { userRepository.findAll(baseSpecificationMock) }.returns(emptyList())

        userService.getUsers()

        verify { baseSpecificationMock wasNot Called }
        verify { userRepository.findAll(baseSpecificationMock) }
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `getUsers should  filter on hasDevice true if provided`(hasDeviceValue: Boolean) {
        mockkObject(UserRepository.Companion)
        mockkStatic(Specification::class)
        val baseSpecificationMock: Specification<UserEntity> = mockk()
        every { Specification.where<UserEntity>(null) }.returns(baseSpecificationMock)
        every { baseSpecificationMock.and(any()) }.returns(baseSpecificationMock)
        every { userRepository.findAll(baseSpecificationMock) }.returns(emptyList())

        val hasDeviceSpecificationMock: Specification<UserEntity> = mockk()
        every { UserRepository.hasDevice(any()) }.returns(hasDeviceSpecificationMock)


        userService.getUsers(hasDeviceValue)

        verify { UserRepository.hasDevice(hasDeviceValue) }
        verify { baseSpecificationMock.and(hasDeviceSpecificationMock) }
        verify { userRepository.findAll(baseSpecificationMock) }
    }

    @Test
    fun `createUser should save a new user`() {
        val userEntity = getUserEntitySample()
        every { userRepository.save(userEntity) } returns userEntity

        val result = userService.createUser(userEntity)

        assertSame(userEntity, result)
        verify { userRepository.save(userEntity) }
    }

    @Test
    fun `getUser should return the user when found`() {
        val userEntity = getUserEntitySample()
        every { userRepository.findByIdOrNull(userEntity.id) } returns userEntity

        val result = userService.getUser(userEntity.id)

        assertSame(userEntity, result)
        verify { userRepository.findByIdOrNull(userEntity.id) }
    }

    @Test
    fun `getUser should throw NotFoundException when user is not found`() {
        val userId = UUID.randomUUID()

        every { userRepository.findByIdOrNull(userId) } returns null

        assertThrows<IllegalArgumentException> { userService.getUser(userId) }
        verify { userRepository.findByIdOrNull(userId) }
    }
}
