plugins {
    `android-library`
    `kotlin-android`
}

apply<MainGradlePlugin>()

android {
    namespace = "com.clastic.authentication"
}

dependencies {
    // Hilt
    hilt()

    // Compose
    compose()

    // Module Implementation
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))

    // Material Icons Extended
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    // Firebase
    implementation("com.google.firebase:firebase-auth:23.0.0")

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}