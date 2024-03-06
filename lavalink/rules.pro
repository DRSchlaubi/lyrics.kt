# These references are only used when debugging
-dontwarn io.ktor.**
-dontwarn dev.schlaubi.lyrics.lavaplayer.LavaplayerUtil
# Compile only annotations
-dontwarn kotlinx.coroutines.**
-dontwarn lavalink.server.**

# Kotlin serialization looks up the generated serializer classes through a function on companion
# objects. The companions are looked up reflectively so we need to explicitly keep these functions.
-keepclasseswithmembers class **.*$Companion {
    kotlinx.serialization.KSerializer serializer(...);
}
# If a companion has the serializer function, keep the companion field on the original type so that
# the reflective lookup succeeds.
-keepattributes InnerClasses
-if class **.*$Companion {
  kotlinx.serialization.KSerializer serializer(...);
}
-keepclassmembers class <1>.<2> {
  <1>.<2>$Companion Companion;
}

-keepnames class io.ktor.utils.io.pool.DefaultPool {
    volatile <fields>;
}

# needed by spring
-keepdirectories
-keepattributes RuntimeVisibleParameterAnnotations, RuntimeVisibleAnnotations
-keep class io.ktor.client.HttpClientEngineContainer, io.ktor.client.engine.java.JavaHttpEngineContainer {
    <clinit>();
}

-keep @org.springframework.stereotype.Component class dev.schlaubi.lyrics.lavalink.** {
    <fields>;
    <methods>;
}
-keep @org.springframework.web.bind.annotation.RestController class dev.schlaubi.lyrics.lavalink.** {
    <methods>;
}

-keep class dev.schlaubi.lyrics.protocol.** {*;}

-optimizations !class/unboxing/enum
-printmapping build/libs/mapping.txt
