buildscript {
   repositories {
      google()
      mavenCentral()
   }
   dependencies {
      classpath(libs.androidPluginGradle)
      classpath(libs.kotlin.pluginGradle)
      classpath(libs.dagger.hilt.plugin)
   }
}
