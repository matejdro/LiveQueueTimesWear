plugins {
   id("com.android.application")
   kotlin("android")

   alias(libs.plugins.ksp)
}

android {
   compileSdk = 32

   defaultConfig {
      applicationId = "com.matejdro.livequeuetimes.wear"
      minSdk = 26
      targetSdk = 31

      versionCode = 1
      versionName = "1.0"
   }

   buildFeatures {
      compose = true
   }

   compileOptions {
      sourceCompatibility(JavaVersion.VERSION_1_8)
      targetCompatibility(JavaVersion.VERSION_1_8)
   }

   composeOptions {
      kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
   }

   kotlinOptions {
      jvmTarget = "1.8"
   }
}


dependencies {
   implementation(libs.androidx.activity.compose)
   implementation(libs.androidx.compose.compiler)
   implementation(libs.androidx.compose.wear.foundation)
   implementation(libs.androidx.compose.wear.material)
   implementation(libs.androidx.compose.wear.navigation)
   debugImplementation(libs.androidx.compose.ui.tooling)
   implementation(libs.androidx.compose.ui.toolingPreview)
   implementation(libs.androidx.wear)
   implementation(libs.logcat)
   implementation(libs.moshi)
   implementation(libs.retrofit)
   implementation(libs.retrofit.moshi)

   ksp(libs.moshi.codegen)
}
