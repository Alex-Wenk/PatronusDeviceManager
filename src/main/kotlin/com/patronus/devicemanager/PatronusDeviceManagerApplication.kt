package com.patronus.devicemanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PatronusDeviceManagerApplication

fun main(args: Array<String>) {
    runApplication<PatronusDeviceManagerApplication>(*args)
}
