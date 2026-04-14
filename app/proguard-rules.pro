# Room rules
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.Entity
-keep class * extends androidx.room.Dao
-keep class * implements androidx.room.RoomDatabase$Callback

# Moshi rules
-keep class com.squareup.moshi.** { *; }
-keep interface com.squareup.moshi.** { *; }
-keep @com.squareup.moshi.Json class *
-keepclassmembers class * {
    @com.squareup.moshi.Json *;
}

# Retrofit rules
-keepattributes Signature, InnerClasses, EnclosingMethod
-keep @retrofit2.http.** interface * { *; }
-keepclassmembers class * {
    @retrofit2.http.** <methods>;
}

# Kotlin Serialization (if used)
-keepattributes *Annotation*, EnclosingMethod, Signature
-keepclassmembers class ** {
    @kotlinx.serialization.SerialName <fields>;
}

# Hilt rules
-keep class dagger.hilt.android.internal.managers.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$ComponentManager
-keep class * implements dagger.hilt.internal.GeneratedComponent
-keep class * implements dagger.hilt.internal.UnsafeCasts
-keep class * implements dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories$InternalFactoryFactory
-keep class * implements dagger.hilt.android.internal.managers.ComponentSupplier

# General Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.android.HandlerContext {
    java.lang.String name;
}
