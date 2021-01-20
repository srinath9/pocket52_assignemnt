package com.hyprful.pocket52assignment.dataSources;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hyprful.pocket52assignment.models.Post;
import com.hyprful.pocket52assignment.services.PostService;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class PostRemoteSource {
    private PostService postService;
    final MutableLiveData<List<Post>> data = new MutableLiveData<>();
      HashMap<Integer, Integer> userCountHash = new HashMap();
    final MutableLiveData<HashMap<Integer, Integer>> relayUserCount = new MutableLiveData<>();


    @Inject
    public PostRemoteSource(PostService postService) {
        this.postService = postService;
    }

    public LiveData<List<Post>> allPosts() {
        Disposable subscribe = postService.allPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    Log.i(getClass().getCanonicalName(), "network request " );
                    data.setValue(result);
                    userCountHash.clear();
                    for (Post post : result) {
                        if (userCountHash.containsKey(post.getUserId()) && post.getUserId() != null) {
                            int userId = post.getUserId();
                            int value = userCountHash.get(userId) + 1;
                            userCountHash.put(userId, value);
                        } else {
                            userCountHash.put(post.getUserId() , 1);
                        }
                    }
                    relayUserCount.setValue(userCountHash);
                }, (error) -> {
                    Log.i(getClass().getCanonicalName(), "network request " + error);
//                    data.setValue(false);
                });

        return data;
    }

    public MutableLiveData<HashMap<Integer, Integer>> userCountData(){
        return relayUserCount;
    }
}
