package ru.netology.nmedia.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Attachment
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.enumeration.AttachmentType

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likes: Int = 0,
    var shares: Int = 0,
    val video: String? = null,
    val toShow: Boolean,
//    val savedOnServer: Boolean
    @Embedded
    var attachment: AttachmentEmbeddable?
) {
    fun toDto(): Post = Post(id, author, authorAvatar, content, published, likes, shares, likedByMe, video, toShow, attachment?.toDto())

    companion object {
        fun fromDto(post: Post): PostEntity =
            with(post) {
                PostEntity(id, author, authorAvatar, content, published, likedByMe, likes, shares, video, toShow, AttachmentEmbeddable.fromDto(post.attachment))
            }
    }
    data class AttachmentEmbeddable(
        var url: String,
        var type: AttachmentType
    ){
        fun  toDto() = Attachment(url, type)
        companion object{
        fun fromDto(dto: Attachment?) = dto?.let {
            AttachmentEmbeddable(it.url, it.type)
        }
    }
}
}

fun List<PostEntity>.toDto(): List<Post> = map(PostEntity::toDto)
fun List<Post>.toEntity(): List<PostEntity> = map(PostEntity::fromDto)