package com.hyprful.pocket52assignment.di.components;


import android.app.Application;

import com.hyprful.pocket52assignment.PostApplication;
import com.hyprful.pocket52assignment.di.modules.ActivityModule;
import com.hyprful.pocket52assignment.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityModule.class,
})
public interface AppComponent  extends AndroidInjector<PostApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }



}
