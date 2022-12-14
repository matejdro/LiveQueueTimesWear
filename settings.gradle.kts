enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
   repositories {
      gradlePluginPortal()
   }
}

dependencyResolutionManagement {
   repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
   repositories {
      google()
      mavenCentral()
      maven("https://jitpack.io")
   }
   versionCatalogs {
      create("libs") {
         from(files("libs.toml"))
      }
   }
}

rootProject.name = "LiveQueueTimesWear"
include(":wear")
