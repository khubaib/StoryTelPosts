package app.storytel.candidate.com

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.storytel.candidate.entity.Photo
import app.storytel.candidate.entity.Post
import app.storytel.candidate.entity.PostAndImages
import com.example.android.marsrealestate.network.PostsApi
import kotlinx.coroutines.*
import timber.log.Timber
import java.net.SocketTimeoutException

class PostsActivityViewModel : ViewModel() {


    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var coroutineJob = Job();

    var posts = MutableLiveData<List<Post>>()

    var photos = MutableLiveData<List<Photo>>()

    var postsAndImages = MutableLiveData<PostAndImages>()


    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [coroutineJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val coroutineScope = CoroutineScope(Dispatchers.Main + coroutineJob)


    private var _navigateToPostDetails = MutableLiveData<Post>()
    val navigateToPostDetails
        get() = _navigateToPostDetails


    fun onPostsClicked(post: Post) {
        _navigateToPostDetails.value = post
    }

    fun onPostsDetailNavigated() {
        _navigateToPostDetails.value = null
    }


    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError


    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown


    init {
        getPostsAndImagesFromNetwork()
    }

    private lateinit var getPostsDeferred: Deferred<List<Post>>
    private lateinit var getPhotossDeferred: Deferred<List<Photo>>

    fun getPostsAndImagesFromNetwork() {
        coroutineScope.launch {

            try {
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
                Timber.d("getPostsandgetPhotos is called");
                getPhotossDeferred = PostsApi.retrofitService.getPhotos()
                getPostsDeferred = PostsApi.retrofitService.getPosts()
                photos.value = getPhotossDeferred.await()
                posts.value = getPostsDeferred.await()
                postsAndImages.value = returnLiveDataPostAndImages(posts, photos)
            } catch (e: SocketTimeoutException) {
                _eventNetworkError.value = true
                Timber.e(e)
            } catch (e: Exception) {
                // we can rather handle IOException, MalformedUrlException and other exceptions and do appropriate actions.
                //catching it all here..just in case.
                _eventNetworkError.value = true
                Timber.e(e)

            }
        }
    }

    private fun returnLiveDataPostAndImages(
        post: LiveData<List<Post>>,
        photo: LiveData<List<Photo>>
    ): PostAndImages {
        return PostAndImages(post, photo)
    }


    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }


    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
        getPhotossDeferred.cancel()
        getPostsDeferred.cancel()
    }

}