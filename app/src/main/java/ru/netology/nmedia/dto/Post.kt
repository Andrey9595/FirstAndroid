package ru.netology.nmedia.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import ru.netology.nmedia.enumeration.AttachmentType

sealed interface FeedItem {
    val id: Long

}

@Parcelize
data class Post(
    override val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var shares: Int = 0,
    val likedByMe: Boolean = false,
    val video: String? = null,
    var toShow: Boolean,
    var attachment: @RawValue Attachment? = null,
    val ownedByMe: Boolean = false,
) : Parcelable, FeedItem

data class Ad(
    override val id: Long,
    val image: String
) : FeedItem

data class Attachment(
    val url: String,
    val type: AttachmentType
)
