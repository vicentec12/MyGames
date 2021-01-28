package br.com.vicentec12.mygames.ui.splash.di;

import br.com.vicentec12.mygames.ui.splash.SplashActivity;
import dagger.Subcomponent;

@Subcomponent
public interface SplashComponent {

    @Subcomponent.Factory
    interface Factory {

        SplashComponent create();

    }

    void inject(SplashActivity mSplashActivity);

}
