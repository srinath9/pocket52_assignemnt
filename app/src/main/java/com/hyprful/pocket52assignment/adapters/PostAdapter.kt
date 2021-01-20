package com.hyprful.pocket52assignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyprful.pocket52assignment.R
import com.hyprful.pocket52assignment.commincation.PostClickListener
import com.hyprful.pocket52assignment.databinding.PostItemLayoutBinding
import com.hyprful.pocket52assignment.models.Post


class PostAdapter(
  var context: Context,
  var postList: ArrayList<Post>,
  var userCountHash: HashMap<Int, Int>,
  var postClickCallback: PostClickListener?
) : ListAdapter<Post, PostAdapter.PupilViewHolder>(DIFF_CALLBACK) {

//  lateinit var  pupilClickCallback : PupilClickCallBack

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PupilViewHolder {
    val binding: PostItemLayoutBinding = DataBindingUtil
      .inflate(
        LayoutInflater.from(parent.context), R.layout.post_item_layout,
        parent, false
      )

//    binding.setCallback(pupilClickCallback)
    return PupilViewHolder(binding);
  }

  override fun getItemCount(): Int {
    if (postList == null)
      return 0;
    return postList.size
  }

  override fun onBindViewHolder(holder: PupilViewHolder, position: Int) {
    holder.binding.setPost(postList[position])
    holder.binding.setPostClickCallback(postClickCallback)
    holder.binding.setUserCount(userCountHash[postList[position].userId] ?: 0)
    holder.binding.executePendingBindings()
  }

  fun setPupilList(posts: ArrayList<Post>) {
    if (this.postList == null) {
      this.postList = posts
      notifyItemRangeInserted(0, postList.size)
    } else {
      val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
          return postList.size
        }

        override fun getNewListSize(): Int {
          return postList.size
        }

        override fun areItemsTheSame(
          oldItemPosition: Int,
          newItemPosition: Int
        ): Boolean {
          return postList.get(oldItemPosition).id ===
              postList.get(newItemPosition).id
        }

        override fun areContentsTheSame(
          oldItemPosition: Int,
          newItemPosition: Int
        ): Boolean {
          val project: Post = postList.get(newItemPosition)
          val old: Post = postList.get(oldItemPosition)
          return project.id === old.id
        }
      })
      this.postList = postList
      result.dispatchUpdatesTo(this)
    }

  }
  companion object {
    private val DIFF_CALLBACK = object :
      DiffUtil.ItemCallback<Post>() {
      // Concert details may have changed if reloaded from the database,
      // but ID is fixed.
      override fun areItemsTheSame(oldPupil: Post,
                                   newConcert: Post) = oldPupil.id == newConcert.id

      override fun areContentsTheSame(oldPupil: Post,
                                      newConcert: Post) = oldPupil == newConcert
    }
  }


  inner class PupilViewHolder(var binding: PostItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
  }
}