plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
}

@Suppress("UnstableApiUsage")
android {
    defaultConfig {
        applicationId = "com.example.base"
        minSdk = Versions.MIN_SDK
        compileSdk = Versions.COMPILE_SDK
        targetSdk = Versions.TARGET_SDK
        multiDexEnabled = true
        versionCode = Versions.versionCode
        versionName = Versions.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    val debugFile = rootProject.file("signing/debug.properties")
//    val releaseFile = rootProject.file("signing/release.properties")
//    if (releaseFile.exists() && debugFile.exists()) {
//        val releaseProperties = java.util.Properties()
//        releaseProperties.load(FileInputStream(releaseFile))
//        val debugProperties = java.util.Properties()
//        debugProperties.load(FileInputStream(debugFile))
//        signingConfigs {
//            create("debug-key") {
//                storeFile = debugProperties["keystore"]?.let { rootProject.file(it) }
//                storePassword = debugProperties["storePassword"]?.toString()
//                keyAlias = debugProperties["keyAlias"]?.toString()
//                keyPassword = debugProperties["keyPassword"]?.toString()
//            }
//            create("release-key") {
//                storeFile = releaseProperties["keystore"]?.let { rootProject.file(it) }
//                storePassword = releaseProperties["storePassword"]?.toString()
//                keyAlias = releaseProperties["keyAlias"]?.toString()
//                keyPassword = releaseProperties["keyPassword"]?.toString()
//            }
//        }
//    }
    flavorDimensions.addAll(listOf("server"))
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            manifestPlaceholders["crashlyticsEnabled"] = false
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            manifestPlaceholders["crashlyticsEnabled"] = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
            buildConfigField("String", "BASE_URL", "\"https://api-dev.homing.com/\"")
        }
        create("staging") {
            applicationIdSuffix = ".staging"
            buildConfigField("String", "BASE_URL", "\"https://api-staging.homing.com/\"")
        }
        create("production") {
            buildConfigField("String", "BASE_URL", "\"https://api.homing.com/\"")
        }
    }

    applicationVariants.all {
        buildConfigField("String", "API_VERSION", "\"v3/\"")
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.multidex:multidex:2.0.1")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    implementation("androidx.navigation:navigation-runtime-ktx:2.5.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    implementation("com.github.bumptech.glide:glide:4.13.2")
    kapt("com.github.bumptech.glide:compiler:4.13.2")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")

    implementation("com.google.firebase:firebase-crashlytics:18.3.5")

    implementation("androidx.viewpager2:viewpager2:1.0.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.2.2")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.1.1")
    testImplementation("io.mockk:mockk:1.10.2")

}