import org.gradle.api.plugins.ExtensionContainer
import org.gradle.kotlin.dsl.dependencies

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

dependencies {
    implementation("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
}
