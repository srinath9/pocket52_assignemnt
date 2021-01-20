package com.hyprful.pocket52assignment.services;

import com.hyprful.pocket52assignment.models.Post;
import com.hyprful.pocket52assignment.models.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostService {

    @GET("/posts")
    Observable<List<Post>> allPosts();

    @GET("/users/{id}")
    Observable<User> fetchUser(@Path("id") int id);

}
