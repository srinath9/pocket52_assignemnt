package com.hyprful.pocket52assignment.models

import com.google.gson.annotations.SerializedName


data class Post(
  @SerializedName("userId")
  var userId: Int? = null,
  @SerializedName("id")
  var id: Int? = null ,
  @SerializedName("title")
  var title: String?  = null,
  @SerializedName("body")
  var body: String? = null
) {
  fun stringExist(search: String): Boolean {
    if(title?.contains(search)!! || body?.contains(search)!!){
      return true
    }
    return false

  }
}