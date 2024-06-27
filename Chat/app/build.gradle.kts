plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.chat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chat"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(files("src\\main\\libs\\jars\\mssql-jdbc-12.6.3.jre8.jar"))
    implementation(files("src\\main\\libs\\jars\\mssql-jdbc-12.6.3.jre8-javadoc.jar"))
    implementation(files("src\\main\\libs\\jars\\mssql-jdbc-12.6.3.jre8-sources.jar"))
    implementation(files("src\\main\\libs\\jars\\mssql-jdbc-12.6.3.jre11.jar"))
    implementation(files("src\\main\\libs\\jars\\mssql-jdbc-12.6.3.jre11-javadoc.jar"))
    implementation(files("src\\main\\libs\\jars\\mssql-jdbc-12.6.3.jre11-sources.jar"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}