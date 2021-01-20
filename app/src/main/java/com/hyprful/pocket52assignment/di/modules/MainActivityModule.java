package com.hyprful.pocket52assignment.di.modules;

import androidx.lifecycle.ViewModelProvider;

import com.hyprful.pocket52assignment.MainActivity;
import com.hyprful.pocket52assignment.dataSources.PostRemoteSource;
import com.hyprful.pocket52assignment.dataSources.UserRemoteSource;
import com.hyprful.pocket52assignment.viewmodels.PostViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {


    @Provides
    PostViewModel provideMainFragmentViewModel(MainActivity mainActivity,
                                               PostRemoteSource postRemoteSource,
                                               UserRemoteSource userRemoteSource){
        PostViewModel postViewModel = new ViewModelProvider(mainActivity).get(PostViewModel.class);
        postViewModel.setPostRemoteSource(postRemoteSource);
        postViewModel.setUserRemoteSource(userRemoteSource);
        return postViewModel;
    }


}
