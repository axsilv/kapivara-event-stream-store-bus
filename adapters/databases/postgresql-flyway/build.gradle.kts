plugins {
    alias(libs.plugins.flyway)
    alias(libs.plugins.kotlin)
}

group = "com.databases.postgresql.flyway"

dependencies {
    implementation("org.postgresql:postgresql:42.7.4")
    implementation(libs.flyway)
}

repositories { mavenCentral() }

flyway {
    user = "eventstore"
    password = "eventstore"
    url = "jdbc:postgresql://localhost:5432/eventstore"
}

buildscript {
    dependencies {
        classpath(libs.flyway)
    }
}
