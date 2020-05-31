package app.storytel.candidate.com

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.storytel.candidate.com.databinding.ActivityDetailsBinding
import app.storytel.candidate.entity.Post
import app.storytel.candidate.util.POST_CLCIKED
import app.storytel.candidate.util.THUMBNAIL_URL
import app.storytel.candidate.util.getExtra
import com.squareup.picasso.Picasso

class PostDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    lateinit var detailsactivityViewModel: PostsDetailsActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        detailsactivityViewModel =
            ViewModelProvider(this).get(PostsDetailsActivityViewModel::class.java)
        binding.viewmodel = detailsactivityViewModel

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this


        setSupportActionBar(binding.toolbar)
        supportActionBar.let {
            it?.setDisplayHomeAsUpEnabled(true)
        }

        val thumbnailUrl: String? = getExtra(THUMBNAIL_URL)
        val post: Post? = getExtra(POST_CLCIKED)

        detailsactivityViewModel.postID = post?.id.toString()
        binding.collapsingToolbar.post { binding.collapsingToolbar.requestLayout() }
        binding.detailRecyclerviews.details.text = post?.body


        val adapter = NewPostDetailsAdapter(post?.title)

        binding.detailRecyclerviews.detailsRecyclerView.adapter = adapter

        // Observer for the network error.
        detailsactivityViewModel.eventNetworkError.observe(
            this,
            Observer<Boolean> { isNetworkError ->
                binding.tryagainButton.visibility = View.VISIBLE
                binding.errorrText.visibility = View.VISIBLE
                if (isNetworkError) onNetworkError() else noNetworkError()

            })


        //Alright..have the response? bind it to the adapter
        detailsactivityViewModel.postcomments.observe(this, Observer {
            it?.let {
                adapter.submitList(it)

            }
        })


        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.detailRecyclerviews.detailsRecyclerView.layoutManager = manager

        //use the posts title as the title for the collapsing toolbar???
        //  binding.collapsingToolbar.title = post.title
        Picasso.get().load(thumbnailUrl).into(binding.backdrop)
    }


    private fun noNetworkError() {
        binding.errorrText.visibility = View.GONE
        binding.tryagainButton.visibility = View.GONE
    }

    /**
     * Method for displaying a  error message for network errors.
     */
    private fun onNetworkError() {
        if (!detailsactivityViewModel.isNetworkErrorShown.value!!) {
            binding.errorrText.visibility = View.VISIBLE
            binding.tryagainButton.visibility = View.VISIBLE
            detailsactivityViewModel.onNetworkErrorShown()
        }
    }
}