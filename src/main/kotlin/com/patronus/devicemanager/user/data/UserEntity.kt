package com.patronus.devicemanager.user.data

import com.patronus.devicemanager.address.data.AddressEntity
import com.patronus.devicemanager.data.BaseEntity
import com.patronus.devicemanager.device.data.DeviceEntity
import jakarta.persistence.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "USERS")
class UserEntity(
        @Id
        override var id: UUID,
        var firstName: String,
        var lastName: String,
        @OneToOne
        @JoinColumn(referencedColumnName = "id")
        @Cascade(CascadeType.ALL)
        var addressEntity: AddressEntity,
        var birthday: LocalDate,
) : BaseEntity(id) {
    @OneToMany
    @JoinColumn(name = "user_id")
    @Cascade(CascadeType.ALL)
    var devices: List<DeviceEntity>? = null


    companion object {
        const val FIRST_NAME = "firstName"
        const val LAST_NAME = "lastName"
        const val ADDRESS = "address"
        const val BIRTHDAY = "birthday"
        const val DEVICES = "devices"
    }

}
