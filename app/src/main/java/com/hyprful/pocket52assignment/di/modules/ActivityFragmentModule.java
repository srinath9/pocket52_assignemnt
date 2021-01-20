package com.hyprful.pocket52assignment.di.modules;

import com.hyprful.pocket52assignment.fragments.PostFragment;
import com.hyprful.pocket52assignment.fragments.UserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityFragmentModule {

    @ContributesAndroidInjector(
//            modules = {
//                    MainFragmentModule.class,
//            }
    )
    public abstract PostFragment bindPostFragment();

    @ContributesAndroidInjector(
//            modules = {
//                    NewPupilModule.class,
//            }
    )
    public abstract UserFragment bindUserFragment();
}
