package app.storytel.candidate.entity

import androidx.lifecycle.LiveData


data class PostAndImages(
    var mPosts: LiveData<List<Post>>,
    var mPhotos: LiveData<List<Photo>>
)



