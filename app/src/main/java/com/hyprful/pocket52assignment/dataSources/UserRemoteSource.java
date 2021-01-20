package com.hyprful.pocket52assignment.dataSources;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.hyprful.pocket52assignment.models.User;
import com.hyprful.pocket52assignment.services.PostService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class UserRemoteSource {

    private final PostService postService;
    final MutableLiveData<User> data = new MutableLiveData<>();

    @Inject
    public UserRemoteSource(PostService postService) {
        this.postService = postService;
    }

    public MutableLiveData<User> fetchUserDetails(int userId){
        postService.fetchUser(userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    data.setValue(result);
                    Log.i(getClass().getCanonicalName(), "network request " + result);
                }, (error) -> {
                    Log.i(getClass().getCanonicalName(), "network request " + error);
//                    data.setValue(false);
                });

        return data;

    }

}
