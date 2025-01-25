group = "com.eventhub.spring.services.configurations"

dependencies {
    implementation(project(":core:eventhub-services"))
    implementation(project(":core:eventhub-domains"))
    implementation(libs.spring.boot)
    implementation(libs.spring.boot.data.r2dbc)
}

