package kz.jibergroup.studyinn.presentation.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.myfirstapp.presentation.global.utils.EventObserver
import kotlinx.android.synthetic.main.error_message_view.*
import kotlinx.android.synthetic.main.error_not_found.*
import kotlinx.android.synthetic.main.home_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.PostItem
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import kz.jibergroup.studyinn.presentation.home.player.VerticalSpacingItemDecorator
import kz.jibergroup.studyinn.presentation.home.player.VideoPlayerRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(), ItemClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun layoutId(): Int {
        return R.layout.home_fragment
    }

    val viewModel: HomeViewModel by viewModel()
    lateinit var postAdapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        initList()

        viewModel.posts.observe(viewLifecycleOwner, postObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errObserver)
//        viewModel.getPost()
        viewModel.initPost()
        initRecyclerView()
    }

    private val postObserver = Observer<MutableList<PostItem>> {
        //        if (it.isNotEmpty()) {
//            handleError(HomeViewModel.Error.Success)
//            postAdapter.submitData(it)
//        } else {
//            handleError(HomeViewModel.Error.Not_found)
//            error404TryAgain.setOnClickListener {
//                recyclerPosts.visibility = View.VISIBLE
//                viewModel.getPost()
//            }
//        }

    }

    private fun initList() {
        postAdapter = PostAdapter()
        postAdapter.submitListener(this)
        postAdapter.submitData(getShimmerData())
        postAdapter.submitContext(activity as AppCompatActivity)

        recyclerPosts.adapter = postAdapter
        recyclerPosts.layoutManager = LinearLayoutManager(activity)

    }


    private fun initRecyclerView() {
        recyclerPosts.layoutManager = LinearLayoutManager(activity)
        val itemDecorator = VerticalSpacingItemDecorator(10)
        recyclerPosts.addItemDecoration(itemDecorator)

        recyclerPosts.setMediaObjects(viewModel.mediaobjects.value ?: arrayListOf())
        val adapter =
            VideoPlayerRecyclerAdapter(viewModel.mediaobjects.value ?: arrayListOf(), initGlide())
        recyclerPosts.setAdapter(adapter)
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
            .placeholder(R.drawable.white_background)
            .error(R.drawable.white_background)

        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }


    private val errObserver = EventObserver<String> {
        handleError(HomeViewModel.Error.ServerError, it)

        errorTryAgain.setOnClickListener {
            recyclerPosts.visibility = View.VISIBLE
            viewModel.getPost()
        }
    }

    private fun handleError(error: HomeViewModel.Error, errorLabel: String? = null) {
        when (error) {
            HomeViewModel.Error.Not_found -> {
                recyclerPosts.visibility = View.GONE
                errorLayout.visibility = View.GONE
                error404Layout.visibility = View.VISIBLE
                error404Description.text = getString(R.string.post_not_found)
            }
            HomeViewModel.Error.ServerError -> {
                recyclerPosts.visibility = View.GONE
                errorLayout.visibility = View.VISIBLE
                errorDescription.text = errorLabel
            }
            HomeViewModel.Error.Success -> {
                errorLayout.visibility = View.GONE
                error404Layout.visibility = View.GONE
                recyclerPosts.visibility = View.VISIBLE
            }
        }
    }


    override fun click(position: Int) {

    }
}
