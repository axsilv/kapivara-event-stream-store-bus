group = "com.kapivara.eventhub.adapters.databases.r2dbc-postgresql"

dependencies {
    implementation(project(":core:eventhub-domains"))
    implementation(libs.r2dbc.postgresql)
    implementation(libs.r2dbc.pool)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.spring.boot)
    implementation(libs.spring.boot.data.r2dbc)
}