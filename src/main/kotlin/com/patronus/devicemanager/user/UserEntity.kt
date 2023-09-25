package com.patronus.devicemanager.user

import com.patronus.devicemanager.address.AddressEntity
import com.patronus.devicemanager.device.DeviceEntity
import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "USERS")
class UserEntity(
        @Id
        var id: UUID,
        var firstName: String,
        var lastName: String,
        @OneToOne
        @JoinColumn(referencedColumnName = "id")
        @Cascade(CascadeType.ALL)
        var addressEntity: AddressEntity,
        var birthday: LocalDate,
)  {
    @OneToMany
    @JoinColumn(name = "user_id")
    @Cascade(CascadeType.ALL)
    var devices: List<DeviceEntity>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as UserEntity).id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
