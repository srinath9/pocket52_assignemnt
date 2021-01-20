package com.hyprful.pocket52assignment.models

import com.google.gson.annotations.SerializedName

data class Geo(
  @SerializedName("lat")
  var lat: Double? = null,
  @SerializedName("lng")
  var lng: Double? = null
)
