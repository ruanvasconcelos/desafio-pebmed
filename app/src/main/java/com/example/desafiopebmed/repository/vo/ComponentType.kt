package com.example.desafiopebmed.repository.vo

import java.util.*


enum class ComponentType(private val value: String) {
    HEADER_TITLE("header_title"),

    GRID_THUMB("grid_thumb"),

    UNKNOWN("unknown");

    fun safeValueOf(value: String): ComponentType {
        for (enumValue in values()) {
            if (enumValue.value == value.toLowerCase(Locale.getDefault())) {
                return enumValue
            }
        }
        return UNKNOWN
    }
}