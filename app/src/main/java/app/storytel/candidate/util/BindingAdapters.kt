package app.storytel.candidate.util

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Binding adapter used to hide the spinner once data is available.
 */
@BindingAdapter("isNetworkError", "posts")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, postsOrComments: Any?) {
    view.visibility =
        if (postsOrComments != null) {
            View.GONE
        }
         else {
            View.VISIBLE
        }

    if(isNetWorkError) {
        view.visibility = View.GONE
    }
}
