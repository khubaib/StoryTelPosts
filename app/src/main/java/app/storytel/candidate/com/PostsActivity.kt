package app.storytel.candidate.com

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.storytel.candidate.com.databinding.ActivityMainBinding
import app.storytel.candidate.util.POST_CLCIKED
import app.storytel.candidate.util.THUMBNAIL_URL
import app.storytel.candidate.util.gotoDetailsActivity
import timber.log.Timber


class PostsActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: PostsActivityViewModel
    private var postsAdapter: PostsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(PostsActivityViewModel::class.java)
        binding.toolbarLayout.post { binding.toolbarLayout.requestLayout() }
        binding.viewmodel = viewModel
        Timber.d("onCreate")
        var urlClicked: String? = null

        postsAdapter = PostsAdapter(
            PostsAdapter.PostsClickListener { postId, url ->
                urlClicked = url;
                viewModel.onPostsClicked(postId)
            })

        // Observer for the network error.
        viewModel.eventNetworkError.observe(this, Observer<Boolean> { isNetworkError ->
            binding.tryagainButton.visibility = View.VISIBLE
            binding.errorrText.visibility = View.VISIBLE
            if (isNetworkError) onNetworkError() else noNetworkError()
        })

        viewModel.navigateToPostDetails.observe(this, Observer { post ->
            post?.let {
                //Lets navigate through extension functions to make it look more clean!!
                this.gotoDetailsActivity(PostDetailsActivity::class,extras = mapOf(THUMBNAIL_URL to urlClicked, POST_CLCIKED to post))
                viewModel.onPostsDetailNavigated();

            }
        })

        binding.recyclerviews.recyclerView.adapter = postsAdapter



        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this


        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerviews.recyclerView.layoutManager = manager

    }

    private fun noNetworkError() {
        binding.tryagainButton.visibility = View.GONE
        binding.errorrText.visibility = View.GONE
    }

    /**
     * Method for displaying a  error message for network errors.
     */
    private fun onNetworkError() {
            if (!viewModel.isNetworkErrorShown.value!!) {
                binding.errorrText.visibility = View.VISIBLE
                binding.tryagainButton.visibility = View.VISIBLE
                viewModel.onNetworkErrorShown()
            }
    }

    override fun onResume() {
        super.onResume()
        //Alright..have the response? bind it to the adapter
        viewModel.postsAndImages.observe(this, Observer {
            it?.let {
                postsAdapter?.setData(it)

            }
        })
    }


}
