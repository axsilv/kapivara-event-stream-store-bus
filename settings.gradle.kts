plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "kapivara-event-stream-store-bus"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    "deployments:spring:event-store-queries",
    "deployments:spring:event-store-commands",
    "deployments:spring:event-store-worker",
    "deployments:spring:event-store-worker-dlq",
    "deployments:spring:event-bus-commands",
    "deployments:spring:event-bus-queries",
    "deployments:spring:event-bus-webhooks",
    "core:kapivara-domains",
    "core:kapivara-business",
    "adapters:database",
    "adapters:web:eventstore:commands:spring:router",
    "adapters:web:eventstore:queries:spring:router",
)
