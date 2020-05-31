package app.storytel.candidate.util

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import kotlin.reflect.KClass

 const val THUMBNAIL_URL = "thumbnailUrl"
 const val POST_CLCIKED = "postclicked"


fun Activity.gotoDetailsActivity(cls: KClass<out Activity>, extras: Map<String, Any?>? = null) {
    val intent = Intent(this, cls.java)
    extras?.forEach { intent.addExtra(it.key, it.value) }
    startActivity(intent)
}

fun Intent.addExtra(key: String, value: Any?) {
    when (value) {
        is String -> putExtra(key, value)
        is Parcelable -> putExtra(key, value)
        //Add other types when needed
    }
}

inline fun <reified T> Activity.getExtra(extra: String): T? {
    return intent.extras?.get(extra) as? T?
}