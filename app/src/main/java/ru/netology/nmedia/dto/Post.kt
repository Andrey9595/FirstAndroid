package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var shares: Int = 0,
    val likedByMe: Boolean = false,
    val video: String? = null,
    var toShow: Boolean
//    var attachment: Attachment? = null,
//    var savedOnServer: Boolean = false
)

data class Attachment(
    val url: String,
    val description: String?,
    val type: AttachmentType,
)

enum class AttachmentType {
    IMAGE
}