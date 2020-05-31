package app.storytel.candidate.com

import android.app.Application
import timber.log.Timber

class PostsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //using Timeber for wasy Logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}