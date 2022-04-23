package com.nyt.nytnews.data

import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonPrimitive
import java.text.SimpleDateFormat


val TIME_LONG_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

fun getTimestamp(timestamp: String, format: String = TIME_LONG_FORMAT): Long {
    val parser = SimpleDateFormat(format)
    return parser.parse(timestamp).time
}

object LongToStringSerializer :
    JsonTransformingSerializer<String>(tSerializer = String.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return JsonPrimitive(value = element.toString())
    }
}

object ShortTimestampToMillisSerializer :
    JsonTransformingSerializer<Long>(tSerializer = Long.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return JsonPrimitive(value = getTimestamp(element.jsonPrimitive.content, "yyyy-MM-dd"))
    }
}

object LongTimestampToMillisSerializer :
    JsonTransformingSerializer<Long>(tSerializer = Long.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return JsonPrimitive(value = getTimestamp(element.jsonPrimitive.content))
    }
}