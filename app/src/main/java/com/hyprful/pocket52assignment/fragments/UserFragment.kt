package com.hyprful.pocket52assignment.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.hyprful.pocket52assignment.R
import com.hyprful.pocket52assignment.adapters.PostAdapter
import com.hyprful.pocket52assignment.databinding.UserFragmentBinding
import com.hyprful.pocket52assignment.models.Post
import com.hyprful.pocket52assignment.models.User
import com.hyprful.pocket52assignment.viewmodels.PostViewModel
import dagger.android.support.DaggerFragment

class UserFragment : DaggerFragment() {

  private var userId: Int = 0
  private lateinit var postViewModel: PostViewModel
  private lateinit var postAdapter: PostAdapter

  private var userCountHash: HashMap<Int, Int> = HashMap()
  private  var postList: ArrayList<Post> = ArrayList();

  private  var user : User = User()


  companion object {
    fun newInstance() =
      UserFragment()
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
    userId = arguments?.get("userId") as Int

    postViewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)

    val binding: UserFragmentBinding =
      DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false)

    binding.postViewModel = postViewModel

    val inflate = inflater.inflate(R.layout.user_fragment, container, false)
    initializeUiElements(inflate)
    return inflate
  }

  private fun initializeUiElements(inflate: View) {

    var tvName = inflate.findViewById<TextView>(R.id.tvName)
    var tvPhone = inflate.findViewById<TextView>(R.id.tvPhone)
    var tvCompanyName = inflate.findViewById<TextView>(R.id.tvCompanyName)
    var tvStreet = inflate.findViewById<TextView>(R.id.tvStreet)
    var tvEmail = inflate.findViewById<TextView>(R.id.tvEmail)

    postViewModel.fetchUser(userId)?.observe(viewLifecycleOwner, Observer { data ->
      user = data
      tvName.setText( user.name )
      tvEmail.setText( user.email )
      tvCompanyName.setText( user.company?.name )
      tvPhone.setText( user.phone )
      tvStreet.setText( user.address?.street )
    })

    userCountHash = postViewModel.fetchUserCountData().value!!



    var rvPost = inflate.findViewById<RecyclerView>(R.id.rvPosts)
    postAdapter = PostAdapter(activity as Context, postList, userCountHash, null)

    rvPost.adapter = postAdapter
  }


  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    var data = postViewModel.filterUserPosts(userId)
    postList.clear()
    postList.addAll(data)
  }



}