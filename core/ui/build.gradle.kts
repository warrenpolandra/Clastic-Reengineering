plugins {
    `android-library`
    `kotlin-android`
}

apply<MainGradlePlugin>()

android {
    namespace = "com.clastic.ui"
}

dependencies {
    // Hilt
    hilt()

    // Compose UI
    compose()

    // Module Implementation
    implementation(project(":core:model"))
    implementation(project(":core:utils"))

    // Coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Material Icons Extended
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    // Compose Activity
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))

    // Firebase
    implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}