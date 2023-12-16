package dev.schlaubi.lyrics.protocol

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlin.properties.Delegates

/**
 * Serializable version of a [LongRange].
 */
public typealias SerializableLongRange = @Serializable(with = LongRangeSerializer::class) LongRange

/**
 * Serializer for [SerializableLongRange].
 */
public object LongRangeSerializer : KSerializer<LongRange> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("dev.schlaubi.lyrics.protocol.LongRange") {
        element<Long>("start")
        element<Long>("end")
    }

    override fun deserialize(decoder: Decoder): LongRange {
        var start by Delegates.notNull<Long>()
        var end by Delegates.notNull<Long>()

        decoder.decodeStructure(descriptor) {
            while (true) {
                when (decodeElementIndex(descriptor)) {
                    0 -> start = decodeLongElement(descriptor, 0)
                    1 -> end = decodeLongElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                }
            }
        }

        return start..end
    }

    override fun serialize(encoder: Encoder, value: LongRange): Unit = encoder.encodeStructure(descriptor) {
        encodeLongElement(descriptor, 0, value.first)
        encodeLongElement(descriptor, 1, value.last)
    }
}
