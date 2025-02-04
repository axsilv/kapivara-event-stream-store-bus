plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.plugin.spring)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.serializationp)
}

group = "com.kapivara.eventhub"
version = "0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.bootJar {
    enabled = false
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain(17)
}


subprojects {
    if ( file("src/main/kotlin").isDirectory || file("src/main/resource").isDirectory) {
        apply {
            plugin("org.jetbrains.kotlin.jvm")
            plugin("org.jmailen.kotlinter")
            plugin("org.jetbrains.kotlin.plugin.serialization")
        }

        dependencies {
            implementation(rootProject.libs.kotlinx.coroutines)
            implementation(rootProject.libs.kotlinx.datetime)
            implementation(rootProject.libs.kotlinx.collections.immutable)
            implementation(rootProject.libs.kotlinx.coroutines.reactor)
            implementation(rootProject.libs.serialization.json)
            implementation(rootProject.libs.serialization.core)
            implementation(rootProject.libs.kotlin.logging)

            testImplementation(rootProject.libs.kotlinx.coroutines.test)
            testImplementation(rootProject.libs.kotest.junit5)
            testImplementation(rootProject.libs.kotest.assertions)
            testImplementation(rootProject.libs.kotest.property)
            testImplementation(rootProject.libs.mockk)
        }

        tasks.test {
            useJUnitPlatform()
            jvmArgs(
                "--add-opens", "java.base/java.lang=ALL-UNNAMED",
                "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED"
            )
        }

        repositories {
            mavenCentral()
        }

        kotlin {
            jvmToolchain(17)
        }

        java {
            version = 17
        }
    }
}
