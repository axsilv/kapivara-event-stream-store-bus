group = "com.eventstore.commands"

dependencies {
    implementation(libs.spring.boot.starter.webflux)
    implementation(project(":core:kapivara-business"))
}
