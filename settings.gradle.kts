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
include(":core:model")
include(":core:domain")
include(":feature:splashscreen")
include(":feature:authentication")
