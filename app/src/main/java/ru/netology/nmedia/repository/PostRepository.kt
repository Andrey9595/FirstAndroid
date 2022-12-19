package ru.netology.nmedia.repository


import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(id: Long): Post
    fun shareById(id: Long): Post
    fun save(post: Post): Post
    fun removeById(id: Long): Post
    fun getPost(id: Long): Post
    fun getAllAsync(callback: GetAllCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }
}