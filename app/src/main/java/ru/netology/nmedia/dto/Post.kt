package ru.netology.nmedia.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import ru.netology.nmedia.enumeration.AttachmentType

@Parcelize
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
    var toShow: Boolean,
    var attachment: @RawValue Attachment? = null,
//    var savedOnServer: Boolean = false
) : Parcelable

data class Attachment(
    val url: String,
//    val description: String?,
    val type: AttachmentType
)
