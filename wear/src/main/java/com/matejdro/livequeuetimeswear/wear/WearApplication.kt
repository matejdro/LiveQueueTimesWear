package com.matejdro.livequeuetimeswear.wear

import android.app.Application
import logcat.AndroidLogcatLogger

class WearApplication : Application() {
   override fun onCreate() {
      super.onCreate()

      AndroidLogcatLogger.installOnDebuggableApp(this)
   }
}
