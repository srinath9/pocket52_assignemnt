package com.hyprful.pocket52assignment;

import com.hyprful.pocket52assignment.di.components.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class PostApplication extends DaggerApplication {

    private static PostApplication instance;


    public static synchronized PostApplication getInstance() {
        return instance;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
