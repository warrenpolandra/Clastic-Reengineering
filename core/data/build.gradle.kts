plugins {
    `android-library`
    `kotlin-android`
}

apply<MainGradlePlugin>()

android {
    namespace = "com.clastic.data"
}

dependencies {
    // Hilt
    hilt()

    // Module Dependencies
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:utils"))

    // Firebase
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.11.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}