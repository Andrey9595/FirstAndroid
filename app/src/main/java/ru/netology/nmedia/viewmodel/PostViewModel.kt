package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.utils.SingleLiveEvent

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    authorAvatar = "",
    likedByMe = false,
    published = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    private val edited = MutableLiveData(empty)
    val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<FeedModel>
        get() = _data

    init {
        loodPost()
    }

    fun loodPost() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(posts: List<Post>) {
                _data.value = (FeedModel(posts = posts, empty = posts.isEmpty()))
                println("success")
            }

            override fun onError(e: Exception) {
                _data.value =FeedModel(error = true)
                println("error")
            }
        }, getApplication())
    }

    fun save() {
        var oldPosts = _data.value?.posts.orEmpty()
        edited.value?.let {
            repository.save(it, object : PostRepository.Callback<Post> {
                override fun onSuccess(posts: Post) {
                    oldPosts = listOf(posts)+oldPosts
                    _data.postValue(FeedModel(posts = oldPosts))
                    _postCreated.value = Unit
                }

                override fun onError(e: Exception) {
                    _data.value =FeedModel(error = true)
                }

            })
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        var updatedPost = _data.value?.posts.orEmpty()
        repository.likedById(id, object : PostRepository.Callback<Post> {
            override fun onSuccess(posts: Post) {
                updatedPost = updatedPost.map { post ->
                    if (post.id != id) post else post.copy(
                        likedByMe = post.likedByMe,
                        likes = post.likes
                    )
                }
                _data.value = FeedModel(posts = updatedPost)
            }

            override fun onError(e: Exception) {
                _data.value = FeedModel(error = true)
            }
        })
    }

    fun removeById(id: Long) {
        repository.removeById(id, object : PostRepository.RemCallback {
            override fun onSuccess() {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .filter { it.id != id }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.value = FeedModel(error = true)
            }
        })
    }
    fun getPost(id: Long) = repository.getPost(id)
}