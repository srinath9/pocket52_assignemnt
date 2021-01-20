package com.hyprful.pocket52assignment.commincation

import com.hyprful.pocket52assignment.models.Post

interface PostClickListener {
  fun postClicked(post: Post)
}