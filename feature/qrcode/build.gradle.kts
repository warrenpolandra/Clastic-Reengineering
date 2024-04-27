plugins {
    `android-library`
    `kotlin-android`
}

apply<MainGradlePlugin>()

android {
    namespace = "com.clastic.qrcode"
}

dependencies {
    // Hilt
    hilt()

    // Compose
    compose()

    // Module Implementation
    implementation(project(":core:ui"))
    implementation(project(":core:utils"))
    implementation(project(":core:domain"))

    // CameraX
    implementation("androidx.camera:camera-camera2:1.3.2")
    implementation("androidx.camera:camera-lifecycle:1.3.2")
    implementation("androidx.camera:camera-view:1.3.2")

    // Zxing
    implementation("com.google.zxing:core:3.5.1")

    // Guava
    implementation("com.google.guava:guava:31.0.1-jre")

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}