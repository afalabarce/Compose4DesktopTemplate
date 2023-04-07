package io.github.afalabarce.compose.desktop.models.serializers

import com.google.gson.*
import io.github.afalabarce.compose.desktop.models.utilities.format
import io.github.afalabarce.compose.desktop.models.utilities.iif
import io.github.afalabarce.compose.desktop.models.utilities.supportedDateFormat
import io.github.afalabarce.compose.desktop.models.utilities.toInstant
import java.lang.reflect.Type
import java.text.ParseException
import java.time.Instant

// With this class we can parse some date formats received into json data, and is very simple to extend to new formats
class GsonInstantDeserializer: JsonDeserializer<Instant>, JsonSerializer<Instant> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Instant? {
        for (format in supportedDateFormat){
            try {
                val stringDate = json?.asJsonPrimitive?.asString

                return stringDate?.toInstant(format)
            } catch (_: Exception) {

            }
        }

        throw ParseException("Error parsing date", 0)
    }

    override fun serialize(
        src: Instant?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        return (src == null).iif({ null }, { JsonPrimitive(src.format("yyyy-MM-dd'T'HH:mm:ss")) })
    }
}
