package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(id: Long): Post
    fun shareById(id: Long): Post
    fun save(post: Post): Post
    fun removeById(id: Long): Post
    fun getPost(id: Long) : Post
}