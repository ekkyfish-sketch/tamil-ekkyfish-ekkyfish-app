# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontobfuscate
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclasseswithmembers class * {
    public void set*(...);
}

-keepclasseswithmembers class * {
    public *(android.content.Context, android.util.AttributeSet);
}

# Retrofit
-keep class com.ekkyfish.data.remote.** { *; }
-keep interface com.ekkyfish.data.remote.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Gson
-keep class com.ekkyfish.data.model.** { *; }
-keepclasseswithmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Firebase
-keep class com.google.firebase.** { *; }
-keep interface com.google.firebase.** { *; }

# Room
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }

# Hilt
-keep class * extends dagger.hilt.android.HiltAndroidApp
-keep class dagger.hilt.** { *; }

# Kotlin
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# kotlinx.coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}