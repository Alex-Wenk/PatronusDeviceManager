package com.patronus.devicemanager.device

import com.patronus.devicemanager.device.data.DeviceEntity
import com.patronus.devicemanager.device.data.DeviceRepository
import com.patronus.devicemanager.device.data.getDeviceEntitySample
import com.patronus.devicemanager.user.UserService
import com.patronus.devicemanager.user.data.getUserEntitySample
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@ExtendWith(MockKExtension::class)
class DeviceServiceTest(
        @InjectMockKs
        private val deviceService: DeviceService,
        @MockK
        private val deviceRepository: DeviceRepository,
        @MockK
        private val userService: UserService,
) {

    @Test
    fun `getDevices should return a list of devices from the repository`() {
        val devices = listOf(
                DeviceEntity(UUID.randomUUID(), "Serial 1", "Model 1", "12345"),
                DeviceEntity(UUID.randomUUID(), "Serial 2", "Model 2", "67890")
        )
        every { deviceRepository.findAll() } returns devices

        val result = deviceService.getDevices()

        assert(result == devices)
        verify { deviceRepository.findAll() }
    }

    @Test
    fun `registerDevice should save a device if it's not already registered`() {
        val deviceId = UUID.randomUUID()
        val device = DeviceEntity(deviceId, "Serial 1", "Model 1", "12345")
        every { deviceRepository.findByIdOrNull(deviceId) } returns null
        every { deviceRepository.save(device) } returns device

        val result = deviceService.registerDevice(device)

        assertSame(result, device)
        verify { deviceRepository.findByIdOrNull(deviceId) }
        verify { deviceRepository.save(device) }
    }

    @Test
    fun `registerDevice should throw AlreadyExistsException if the device is already registered`() {
        val deviceId = UUID.randomUUID()
        val device = DeviceEntity(deviceId, "Serial 1", "Model 1", "12345")
        every { deviceRepository.findByIdOrNull(deviceId) } returns device

        assertThrows<IllegalArgumentException> { deviceService.registerDevice(device) }
        verify { deviceRepository.findByIdOrNull(deviceId) }
        verify(exactly = 0) { deviceRepository.save(device) }
    }

    @Test
    fun `assignDeviceToUser should assign a device to a user`() {
        val device = getDeviceEntitySample()
        val user = getUserEntitySample()
        every { deviceRepository.findByIdOrNull(device.id) } returns device
        every { userService.getUser(user.id) } returns user
        every { deviceRepository.save(device) } returns device

        deviceService.assignDeviceToUser(device.id, user.id)

        assertSame(user, device.userEntity)
        verify { deviceRepository.findByIdOrNull(device.id) }
        verify { userService.getUser(user.id) }
        verify { deviceRepository.save(device) }
    }
}
