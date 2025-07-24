import org.gradle.api.plugins.ExtensionContainer
import org.gradle.kotlin.dsl.dependencies

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}


