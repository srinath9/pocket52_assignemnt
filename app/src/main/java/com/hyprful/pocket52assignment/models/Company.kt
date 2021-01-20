package com.hyprful.pocket52assignment.models

import com.google.gson.annotations.SerializedName


data class Company(
  @SerializedName("bs")
  var id: String? = null,
  @SerializedName("catchPhrase")
  var catchPhrase: String? = null,
  @SerializedName("name")
  var name: String? = null
)
