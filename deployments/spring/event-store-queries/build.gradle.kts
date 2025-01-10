plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.spring")
}

group = "com.event.store.queries"

dependencies {
    implementation(libs.spring.boot.starter.webflux)
    implementation(rootProject.libs.spring.boot)
    implementation(rootProject.libs.spring.boot.bom)
    implementation(rootProject.libs.spring.boot.starter)
}
