package com.patronus.devicemanager.user.web

import com.patronus.devicemanager.user.UserService
import com.patronus.devicemanager.user.converter.UserConverter
import com.patronus.devicemanager.user.web.request.CreateUserRequest
import com.patronus.devicemanager.user.web.response.GetUserResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController()
@RequestMapping("/user")
class UserController(
        private val userConverter: UserConverter,
        private val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): GetUserResponse {
        return userConverter.userEntityToGetUserResponse(
                userService.createUser(
                        userConverter.createUserRequestToEntity(request)
                )
        )
    }

    @GetMapping
    fun getUsers(@RequestParam hasDevice: Boolean?): List<GetUserResponse> {
        return userService.getUsers(hasDevice).map { user -> userConverter.userEntityToGetUserResponse(user) }
    }

}