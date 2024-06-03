plugins {
    `android-library`
    `kotlin-android`
}

apply<MainGradlePlugin>()

android {
    namespace = "com.clastic.transaction.reward"
}

dependencies {
    // Hilt
    hilt()

    // Compose
    compose()

    // Module Implementation
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
    implementation(project(":core:utils"))

    // Material Icons Extended
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    // Coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Firebase
    implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}