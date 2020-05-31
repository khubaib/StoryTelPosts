package app.storytel.candidate.com

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.databinding.PostItemBinding
import app.storytel.candidate.entity.Photo
import app.storytel.candidate.entity.Post
import app.storytel.candidate.entity.PostAndImages
import com.squareup.picasso.Picasso

class PostsAdapter(private val clickListener: PostsClickListener) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder?>() {

    private var mData: PostAndImages? = null



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData,mData?.mPosts?.value!!.get(position),mData?.mPhotos?.value!!.get(position),clickListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: PostAndImages?,
            posts: Post,
            photo: Photo,
            clicklistener: PostsClickListener
        ) {
            binding.clickListener = clicklistener
            binding.posts = posts
            binding.photos = photo
            val imageUrl: String? = item?.mPhotos?.value!!.get(adapterPosition).thumbnailUrl
            //love this picaso:-) its so easy and clean
            Picasso.get().load(imageUrl).into(binding.image)

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

    fun setData(data: PostAndImages?) {
        mData = data
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
        return mData?.mPosts?.value?.size ?: 0
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Post]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Post]
     */
    class PostsClickListener(val clickListener: (Pos: Post, thumbnailUrl: String) -> Unit) {
        fun onClick(pos: Post,thumbnailUrl: String) = clickListener(pos,thumbnailUrl)
    }

}