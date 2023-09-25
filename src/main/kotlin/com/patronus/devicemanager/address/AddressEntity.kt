package com.patronus.devicemanager.address

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "ADDRESSES")
@Entity
class AddressEntity(
        @Id
        var id: UUID,
        var addressLine1: String,
        var addressLine2: String?,
        var postalCode: String,
        var city: String,
        var zoneCode: String?,
        var country: String
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                return id == (other as AddressEntity).id
        }

        override fun hashCode(): Int {
                return id.hashCode()
        }
}