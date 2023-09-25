package com.patronus.devicemanager.user.data

import com.patronus.devicemanager.address.data.getAddressEntitySample
import com.patronus.devicemanager.device.data.DeviceEntity
import com.patronus.devicemanager.device.data.getDeviceEntitySample
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDate
import java.util.*

@DataJpaTest
class UserRepositoryTest @Autowired constructor(
        val userRepository: UserRepository,
        val testEntityManager: TestEntityManager

) {

    @Test
    fun `get user filter has device false`() {
        val userEntityWithoutDevice = testEntityManager.persist(UserEntity(
                id = UUID.randomUUID(),
                firstName = "Firstname1",
                lastName = "Lastname1",
                addressEntity = getAddressEntitySample(),
                birthday = LocalDate.of(1990, 1, 1)
        ))

        val userEntityWithDevice = testEntityManager.persist(UserEntity(
                id = UUID.randomUUID(),
                firstName = "Firstname2",
                lastName = "Lastname2",
                addressEntity = getAddressEntitySample(),
                birthday = LocalDate.of(2000, 1, 1),
        ))

        userEntityWithDevice.devices = listOf(DeviceEntity(
                id = UUID.randomUUID(),
                "1",
                "watchy",
                "077572249537"))

        val usersWithoutDevice = userRepository.findAll(UserRepository.hasDevice(false))
        assertEquals(1, usersWithoutDevice.size)
        assertEquals(userEntityWithoutDevice, usersWithoutDevice[0])
    }

    @Test
    fun `get user filter has device true`() {
        val userEntityNoDevice = testEntityManager.persist(UserEntity(
                id = UUID.randomUUID(),
                firstName = "firstname",
                lastName = "lastname",
                addressEntity = getAddressEntitySample(),
                LocalDate.now(),
        ))

        var userEntityWithDevice = UserEntity(
                id = UUID.randomUUID(),
                firstName = "firstname",
                lastName = "lastname",
                addressEntity = getAddressEntitySample(),
                LocalDate.now()
        )
        userEntityWithDevice.devices = listOf(DeviceEntity(
                id = UUID.randomUUID(),
                "1",
                "077572249537",
                "watchy"))

        userEntityWithDevice = testEntityManager.persist(userEntityWithDevice)

        val usersWithDevice = userRepository.findAll(UserRepository.hasDevice(true))
        assertEquals(1, usersWithDevice.size)
        assertEquals(userEntityWithDevice, usersWithDevice[0])
    }

    @Test
    fun `get user with multiple devices`() {

        var userEntityWithDevice = UserEntity(
                id = UUID.randomUUID(),
                firstName = "firstname",
                lastName = "lastname",
                addressEntity = getAddressEntitySample(),
                LocalDate.now()
        )
        userEntityWithDevice.devices = listOf(getDeviceEntitySample(), getDeviceEntitySample())

        userEntityWithDevice = testEntityManager.persist(userEntityWithDevice)

        val usersWithDevice = userRepository.findAll(UserRepository.hasDevice(true))
        assertEquals(1, usersWithDevice.size)
        assertEquals(userEntityWithDevice, usersWithDevice[0])
        assertEquals(2, usersWithDevice[0].devices?.size)
    }
}