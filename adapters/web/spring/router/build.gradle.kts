group = "com.web.spring.router"

dependencies {
    implementation(libs.spring.boot.starter.webflux)

    implementation(project(":core:eventhub-services"))
}