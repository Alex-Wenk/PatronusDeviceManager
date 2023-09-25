package com.patronus.devicemanager.address

import com.patronus.devicemanager.data.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "ADDRESSES")
@Entity
class AddressEntity(
        @Id
        override var id: UUID,
        var addressLine1: String,
        var addressLine2: String?,
        var postalCode: String,
        var city: String,
        var zoneCode: String?,
        var country: String
) : BaseEntity(id)