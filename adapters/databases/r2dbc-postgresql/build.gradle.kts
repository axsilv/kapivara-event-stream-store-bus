group = "com.kapivara.eventhub.adapters.databases.r2dbc-postgresql"

dependencies {
    implementation(project(":core:eventhub-ports"))
    implementation(libs.r2dbc.postgresql)
    implementation(libs.r2dbc.pool)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(rootProject.libs.spring.boot)
}