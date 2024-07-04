// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.gradle.kotlin.dsl.`java-gradle-plugin`
//plugins {
//    alias(libs.plugins.android.application) apply false
//}
plugins {
    `java-gradle-plugin`
}
dependencies {
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
}