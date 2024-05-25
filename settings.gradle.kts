pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Clastic Reengineering"
include(":app")
include(":core:data")
include(":core:ui")
include(":core:domain")
include(":feature:splashscreen")
include(":feature:authentication")
include(":feature:home")
include(":core:model")
include(":feature:article")
include(":feature:mission")
include(":core:utils")
include(":feature:droppoint")
include(":feature:qrcode")
include(":feature:transaction:plastic")
include(":feature:profile")
include(":feature:plastic_knowledge")
include(":feature:leaderboard")
include(":feature:reward")
include(":feature:transaction:reward")
