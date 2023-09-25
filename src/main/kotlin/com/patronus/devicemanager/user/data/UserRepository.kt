package com.patronus.devicemanager.user.data

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {

    companion object {
        fun hasDevice(hasDevice: Boolean): Specification<UserEntity> {
            return Specification<UserEntity> { root, _, criteriaBuilder ->
                if (hasDevice)
                    criteriaBuilder.isNotEmpty(root.get(UserEntity.DEVICES)) else
                    criteriaBuilder.isEmpty(root.get(UserEntity.DEVICES))

            }
        }
    }


}