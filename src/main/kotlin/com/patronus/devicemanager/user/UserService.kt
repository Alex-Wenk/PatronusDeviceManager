package com.patronus.devicemanager.user

import com.patronus.devicemanager.user.data.UserEntity
import com.patronus.devicemanager.user.data.UserRepository
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUsers(hasDevice: Boolean? = null): List<UserEntity> {
        var querySpecification = Specification.where<UserEntity>(null)

        hasDevice?.let {
            querySpecification = querySpecification.and(UserRepository.hasDevice(it))
        }
        // envisaging having multiple other filters available on get user - e.g. search by name / address country / birthday today

        return userRepository.findAll(querySpecification)
    }

    fun createUser(userEntity: UserEntity): UserEntity {
        //TODO: Check against existing same user - maybe register via email
        return userRepository.save(userEntity)
    }

    fun getUser(userId: UUID): UserEntity {
        return userRepository.findByIdOrNull(userId) ?: throw IllegalArgumentException("User with id $userId not found.")
    }

}