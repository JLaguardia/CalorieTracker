plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.prismsoft.tracker_presentation"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.trackerDomain))
    implementation(project(Modules.coreUI))
    implementation(Compose.uiToolingPreview)

    implementation(Coil.coilCompose)
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.6")
}