package app.storytel.candidate.entity

data class PostComments(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)