plugins {
    `android-library`
    `kotlin-android`
}

apply<MainGradlePlugin>()

android {
    namespace = "com.clastic.home"
}

dependencies {
    // Hilt
    hilt()

    // Compose
    compose()

    // Module implementation
    implementation(project(":core:ui"))
    implementation(project(":core:model"))

    implementation("androidx.compose.material:material-icons-extended:1.6.3")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}