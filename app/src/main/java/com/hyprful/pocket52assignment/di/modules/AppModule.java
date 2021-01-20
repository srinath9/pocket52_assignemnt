package com.hyprful.pocket52assignment.di.modules;


import com.hyprful.pocket52assignment.dataSources.PostRemoteSource;
import com.hyprful.pocket52assignment.services.PostService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hyprful.pocket52assignment.constants.ServerConstant.BASE_URL;

@Module
public class AppModule {


    @Singleton
    @Provides
    static Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public static PostService providePupilServerService(Retrofit retrofit) {
        return retrofit.create(PostService.class);
    }

    @Singleton
    @Provides
    public static PostRemoteSource providePupilServerRepository(PostService postService) {
        PostRemoteSource postRemoteSource = new PostRemoteSource(postService);
        return postRemoteSource;
    }

}
