package app.storytel.candidate.com

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.databinding.PostCommentsBinding
import app.storytel.candidate.entity.PostComments

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between list of [PostComments].
 */
class NewPostDetailsAdapter(private val postTitle: String?) :
    ListAdapter<PostComments, NewPostDetailsAdapter.ViewHolder>(
        LinksDiffCallBack()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), postTitle)
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: PostCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: PostComments,
            postTitle: String?
        ) {
            binding.comments = item
            binding.postsTitle = postTitle
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostCommentsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

    }
}


/**
 * Allows the RecyclerView to determine which items have changed when the [List] of [PostComments]
 * has been updated.
 */
class LinksDiffCallBack : DiffUtil.ItemCallback<PostComments>() {

    override fun areItemsTheSame(oldItem: PostComments, newItem: PostComments): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: PostComments, newItem: PostComments): Boolean {
        return oldItem == newItem
    }

}

