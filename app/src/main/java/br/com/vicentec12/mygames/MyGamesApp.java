package br.com.vicentec12.mygames;

import android.app.Application;

import br.com.vicentec12.mygames.di.AppComponent;
import br.com.vicentec12.mygames.di.DaggerAppComponent;

public class MyGamesApp extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.factory().create(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
