package com.hyprful.pocket52assignment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyprful.pocket52assignment.adapters.PostAdapter
import com.hyprful.pocket52assignment.commincation.PostClickListener
import com.hyprful.pocket52assignment.fragments.PostFragment
import com.hyprful.pocket52assignment.fragments.UserFragment
import com.hyprful.pocket52assignment.models.Post
import com.hyprful.pocket52assignment.viewmodels.PostViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity()  {
  var MAINFRAGMENT_TAG = PostFragment::class.simpleName;

  @Inject
  lateinit var  postViewModel : PostViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    supportFragmentManager.beginTransaction()
      .replace(R.id.container, PostFragment.newInstance(), MAINFRAGMENT_TAG)
      .commit()

  }

  fun goToUserFragment(post: Post) {
    val newInstance = UserFragment.newInstance();
    var data = Bundle()
    data.putInt("userId", post.userId ?: 0)
    newInstance.arguments = data

    supportFragmentManager.beginTransaction()
      .replace(R.id.container, newInstance )
      .addToBackStack(MAINFRAGMENT_TAG)
//      .addToBackStack(null)
      .commit()

  }


}