package com.patronus.devicemanager.device

import com.patronus.devicemanager.data.BaseEntity
import com.patronus.devicemanager.user.UserEntity
import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.util.*

@Entity
@Table(name = "DEVICES")
class DeviceEntity(
        @Id
        override val id: UUID,
        val serialNumber: String,
        val model: String,
        var phoneNumber: String,
        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
        @Cascade(CascadeType.ALL)
        var userEntity: UserEntity? = null
) : BaseEntity(id)