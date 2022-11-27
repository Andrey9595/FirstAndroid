package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likes: Int = 0,
    var shares: Int = 0,
    val video: String? = null
) {
    fun toDto(): Post = Post(id, author, content, published, likes, shares, likedByMe, video)

    companion object {
        fun fromDto(post: Post): PostEntity =
            with(post) {
                PostEntity(id, author, content, published, likedByMe, likes, shares, video)
            }
    }
}