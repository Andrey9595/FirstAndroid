package ru.netology.nmedia.repository


import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeByIdAsync(id: Long, callback: GetAllCallback)
    fun shareById(id: Long): Post
    fun saveAsync(post: Post, callback: GetAllCallback)
    fun removeByIdAsync(id: Long, callback: GetAllCallback)
    fun getPost(id: Long): Post
    fun getAllAsync(callback: GetAllCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }
}