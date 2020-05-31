package app.storytel.candidate.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post (
    var userId : Long,
    var id : Int,
    var title: String,
    var body: String
) : Parcelable