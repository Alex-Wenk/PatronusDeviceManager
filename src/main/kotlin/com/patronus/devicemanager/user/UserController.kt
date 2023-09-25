package com.patronus.devicemanager.user

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController()
@RequestMapping("/user")
class UserController(
) {

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): GetUserResponse? {
        return null
    }

    @GetMapping
    fun getUsers(@RequestParam hasDevice: Boolean?): List<GetUserResponse> {
        return emptyList()
    }

}