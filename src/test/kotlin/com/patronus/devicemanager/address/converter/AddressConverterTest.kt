package com.patronus.devicemanager.address.converter

import com.patronus.devicemanager.address.data.getAddressEntitySample
import com.patronus.devicemanager.address.web.AddressDTO
import com.patronus.devicemanager.address.web.getAddressDtoTestSample
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class AddressConverterTest(
        @InjectMockKs
        private val addressConverter: AddressConverter
) {

    @Test
    fun `fromEntityToDTO should convert AddressEntity to AddressDTO`() {
        val entity = getAddressEntitySample()
        val expectedDTO = AddressDTO(
                entity.addressLine1,
                entity.addressLine2,
                entity.postalCode,
                entity.city,
                entity.zoneCode,
                entity.country
        )

        val resultDTO = addressConverter.fromEntityToDTO(entity)

        assertEquals(expectedDTO, resultDTO)
    }

    @Test
    fun `fromDtoToEntity should convert AddressDTO to AddressEntity`() {
        val dto = getAddressDtoTestSample()

        val resultEntity = addressConverter.fromDtoToEntity(dto)

        assertEquals(dto.addressLine1, resultEntity.addressLine1)
        assertEquals(dto.addressLine2, resultEntity.addressLine2)
        assertEquals(dto.postalCode, resultEntity.postalCode)
        assertEquals(dto.city, resultEntity.city)
        assertEquals(dto.zoneCode, resultEntity.zoneCode)
        assertEquals(dto.country, resultEntity.country)
        assertNotNull(resultEntity.id)
    }
}
