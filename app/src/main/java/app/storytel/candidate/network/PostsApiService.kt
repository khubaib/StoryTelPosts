package com.example.android.marsrealestate.network


import app.storytel.candidate.entity.Photo
import app.storytel.candidate.entity.Post
import app.storytel.candidate.entity.PostComments
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */


//using the MoshiConverterFactory. We can use GsconConverterFactory as well. Trying out moshi and its great as well
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()



/**
 * A public interface that exposes all the getPosts method
 */
interface PostsApiService {


    /**
     * Returns a Coroutine [Deferred] [List] of [Post] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "posts" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("posts")
    fun getPosts():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<Post>>


    /**
     * Returns a Coroutine [Deferred] [List] of [Photo] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("photos")
    fun getPhotos():
            Deferred<List<Photo>>

    /**
     * Returns a Coroutine [Deferred] [List] of [PostComments] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "comments" endpoint will be requested with the GET
     * HTTP method for a particular post id
     */

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: String?):
            Deferred<List<PostComments>>

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object PostsApi {
    val retrofitService : PostsApiService by lazy {
        retrofit.create(PostsApiService::class.java) }
}
