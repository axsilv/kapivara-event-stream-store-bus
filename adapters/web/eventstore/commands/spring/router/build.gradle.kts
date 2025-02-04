group = "com.eventstore.commands"

dependencies {
    implementation(libs.spring.boot.starter.webflux)
    implementation(project(":core:eventhub-spring-services-configurations"))
    implementation(project(":core:eventhub-business"))
}
