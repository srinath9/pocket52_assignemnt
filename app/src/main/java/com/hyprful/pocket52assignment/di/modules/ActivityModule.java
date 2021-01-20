package com.hyprful.pocket52assignment.di.modules;


import com.hyprful.pocket52assignment.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(
            modules = {
                    MainActivityModule.class,
                    ActivityFragmentModule.class,
            }
    )
    abstract MainActivity contributeMainActivity();

}
