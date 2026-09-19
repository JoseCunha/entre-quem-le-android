# Proguard / R8 Configuration for Entre Quem Lê

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keepclassmembers class * {
    *** Companion;
}
-keepclasseswithmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,allowobfuscation,allowshrinking class * {
    <init>(...);
}
-keepclassmembers class pt.cmvilareal.entrequemle.data.** {
    <fields>;
}

# Android & Jetpack Compose rules
-keep class androidx.compose.material.icons.** { *; }
