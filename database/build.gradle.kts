import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    annotationProcessor(Dependencies.roomCompiler)

    Dependencies.databaseDependencies.forEach {
        when (it) {
            is DependencyConfig.Implementation -> implementation(it.name)
            is DependencyConfig.TestImplementation -> testImplementation(it.name)
            is DependencyConfig.AndroidTestImplementation -> androidTestImplementation(it.name)
            is DependencyConfig.Kapt -> kapt(it.name)
        }
    }
}