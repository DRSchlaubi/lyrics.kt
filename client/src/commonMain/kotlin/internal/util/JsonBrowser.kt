package dev.schlaubi.lyrics.internal.util

import kotlinx.serialization.json.*

internal fun JsonObject.getString(key: String): String? {
    val primitive = this[key]?.jsonPrimitive ?: return null
    require(primitive.isString) { "String expected" }
    return primitive.content
}

internal fun JsonObject.getLong(key: String): Long? {
    val primitive = this[key]?.jsonPrimitive ?: return null
    return primitive.content.toLong()
}
internal fun JsonObject.getInt(key: String): Int? {
    val primitive = this[key]?.jsonPrimitive ?: return null
    return primitive.content.toInt()
}

internal fun JsonObject.getJsonObject(key: String) = this[key]?.jsonObject
internal fun JsonArray.getJsonObject(index: Int) = getOrNull(index)?.jsonObject
internal fun JsonObject.getJsonArray(key: String) = this[key]?.jsonArray

internal fun JsonObject.getRunningText(key:String) = getJsonObject(key)
    ?.getJsonArray("runs")?.joinToString(" ") { it.jsonObject.getString("text").toString() }
