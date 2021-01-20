package com.hyprful.pocket52assignment.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.hyprful.pocket52assignment.MainActivity
import com.hyprful.pocket52assignment.R
import com.hyprful.pocket52assignment.adapters.PostAdapter
import com.hyprful.pocket52assignment.commincation.PostClickListener
import com.hyprful.pocket52assignment.models.Post
import com.hyprful.pocket52assignment.viewmodels.PostViewModel
import dagger.android.support.DaggerFragment
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PostFragment : DaggerFragment(){

  private var tvError: TextView? = null
  private lateinit var postViewModel: PostViewModel
  private var userCountHash: HashMap<Int, Int> = HashMap()
  private  var postList: ArrayList<Post> = ArrayList();


  private lateinit var postAdapter: PostAdapter

  var TAG = javaClass.canonicalName



  companion object {
    fun newInstance() =
      PostFragment()
  }

//  private lateinit var viewModel: MainViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
    val inflate = inflater.inflate(R.layout.post_fragment, container, false)
    var rvPost = inflate.findViewById<RecyclerView>(R.id.rvPosts)
    tvError = inflate.findViewById<TextView>(R.id.tvError)
    postAdapter = PostAdapter(activity as Context, postList, userCountHash, postClickCallback)
    searchTextInitializer(inflate)

    rvPost.adapter = postAdapter

    postViewModel = ViewModelProvider(requireActivity()).get(PostViewModel::class.java)
    postViewModel.serverFetch()
    return inflate
  }


  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    postViewModel.fetchData().observe(viewLifecycleOwner,  object : Observer<List<Post>> {
      override fun onChanged(result: List<Post>) {
        postList.clear()
        postList.addAll(result);
        postAdapter.notifyDataSetChanged()

        if (postList.size > 0){
          hideError()
        }else{
          showError()
        }
      }
    })

    postViewModel.fetchUserCountData().observe(viewLifecycleOwner, Observer { userHash ->
      userHash.forEach { key, value ->
        userCountHash[key] = value
      }
    })


  }

  private val postClickCallback: PostClickListener = object : PostClickListener {
    override fun postClicked(post: Post) {

        (activity as MainActivity).goToUserFragment(post)
    }
  }

  fun hideError(){
    tvError?.visibility = View.GONE
  }

  fun showError(){
    tvError?.visibility = View.VISIBLE
  }


  private fun searchTextInitializer(inflate: View) {


    val searchTextWatcher: TextWatcher = object : TextWatcher {
      private  var timer = Timer()

      override fun afterTextChanged(editable: Editable) {
        // user typed: start the timer
        timer = Timer()
        timer.schedule(object : TimerTask() {
          override fun run() {
            // call your API Here,
            postViewModel.searchText(editable.toString())

          }
        }, 600) // 600ms delay before the timer executes the „run“ method from TimerTask
      }

      override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
      ) {
        // nothing to do here
      }

      override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
      ) {
        // user is typing: reset already started timer (if existing)
        if (timer != null) {
          timer.cancel()
        }
      }
    }

    var etSearch =  inflate.findViewById<EditText>(R.id.etSearch);
    etSearch.addTextChangedListener(searchTextWatcher)

  }

}