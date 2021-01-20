package com.hyprful.pocket52assignment.viewmodels

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyprful.pocket52assignment.models.Post
import com.hyprful.pocket52assignment.dataSources.PostRemoteSource
import com.hyprful.pocket52assignment.dataSources.UserRemoteSource
import com.hyprful.pocket52assignment.models.User
import kotlinx.coroutines.launch


class PostViewModel : ViewModel() {

    lateinit var postRemoteSource: PostRemoteSource;
    lateinit var userRemoteSource: UserRemoteSource;
    var allPosts : List<Post> = ArrayList()
    var relayPostList : MutableLiveData<List<Post>> = MutableLiveData()
    var relayPostUserCount : MutableLiveData<HashMap<Int, Int>> = MutableLiveData()

     var user : User = User()




    fun serverFetch() {
        postRemoteSource.allPosts().observeForever{ result ->
            updatePostsFromNetwork(result)
        }
    }

    fun updatePostsFromNetwork(result: MutableList<Post>) {
        allPosts = result
        relayPostList.value = allPosts

    }

    fun fetchData(): MutableLiveData<List<Post>> {
        return relayPostList
    }

    fun fetchUserCountData(): MutableLiveData<HashMap<Int, Int>> {
        return postRemoteSource.userCountData()
    }

    @WorkerThread
    fun searchText(search : String){
        var duplicateData : ArrayList<Post> = ArrayList();
        allPosts?.forEach{ post ->
            if(post.stringExist(search)){
                duplicateData.add(post)
            }
        }

        viewModelScope.launch {
            relayPostList.value = duplicateData
        }
    }

    fun filterUserPosts(userId: Int): ArrayList<Post> {
        var filterResult : ArrayList<Post> = ArrayList()
        allPosts.forEach { post ->
            if( post.userId == userId) {
                filterResult.add(post)
            }
        }
        return filterResult
    }

    fun fetchUser(userId: Int): MutableLiveData<User>? {
        val fetchUserDetails = userRemoteSource.fetchUserDetails(userId)
        fetchUserDetails.observeForever { user = it }
        return  fetchUserDetails
    }


    override fun onCleared() {
        super.onCleared()

    }

}