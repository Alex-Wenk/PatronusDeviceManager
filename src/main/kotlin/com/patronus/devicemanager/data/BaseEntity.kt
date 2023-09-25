package com.patronus.devicemanager.data

import java.util.UUID

open class BaseEntity (open val id: UUID? = null) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as BaseEntity).id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}