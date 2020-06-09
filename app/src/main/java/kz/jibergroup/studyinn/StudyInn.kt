package kz.jibergroup.studyinn

import android.app.Application
import android.content.res.Configuration
import com.example.myfirstapp.di.appModule
import com.example.myfirstapp.di.networkModule
import com.example.myfirstapp.di.uiModule
import kz.jibergroup.studyinn.utils.LocaleManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StudyInn : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@StudyInn)
            modules(listOf(uiModule, networkModule, appModule))
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.setLocale(this)
    }

}