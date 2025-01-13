group = "com.eventstore.queries"

dependencies {
    implementation(libs.spring.boot.starter.webflux)
    implementation(project(":core:eventhub-ports"))
}