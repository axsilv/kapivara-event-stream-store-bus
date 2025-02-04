group = "com.eventstore.queries"

dependencies {
    implementation(libs.spring.boot.starter.webflux)
    implementation(project(":adapters:spring-services"))
    implementation(project(":core:eventhub-business"))
}