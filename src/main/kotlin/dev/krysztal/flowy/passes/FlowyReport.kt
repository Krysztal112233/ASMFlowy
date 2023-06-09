package dev.krysztal.flowy.passes

import com.google.gson.JsonObject
import dev.krysztal.flowy.Identifier

interface FlowyReport {
    val reportName: Identifier
    fun fromJson(json: JsonObject) {}
    fun toJson(): JsonObject? = null
}
