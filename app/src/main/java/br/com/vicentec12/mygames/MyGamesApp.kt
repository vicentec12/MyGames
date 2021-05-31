package br.com.vicentec12.mygames

import android.app.Application
import br.com.vicentec12.mygames.di.AppComponent
import br.com.vicentec12.mygames.di.DaggerAppComponent

class MyGamesApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

}