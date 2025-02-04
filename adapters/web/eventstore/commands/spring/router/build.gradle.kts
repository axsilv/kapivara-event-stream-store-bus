group = "com.eventstore.commands"

dependencies {
    implementation(libs.spring.boot.starter.webflux)
    implementation(project(":adapters:spring-services"))
    implementation(project(":core:eventhub-business"))
}
