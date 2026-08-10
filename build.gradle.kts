plugins {
    id("com.android.library") version "8.1.1"
    kotlin("android") version "1.8.22"
}

android {
    namespace = "com.cinevision.plugin"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }
}

dependencies {
    implementation("com.lagradost:cloudstream3:3.0.0") // Biblioteca base do CloudStream
    implementation("org.jsoup:jsoup:1.16.1") // Raspador de sites
}

