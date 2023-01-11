package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.api.ApiError
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
    val data: LiveData<FeedModel>
        get() = _data
    val postCreated: LiveData<Unit>
        get() = _postCreated
    private val _postCreateError = SingleLiveEvent<ApiError>()
    val postCreateError: LiveData<ApiError>
    get() = _postCreateError

    init {
        loodPost()
    }

    fun loodPost() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.Callback<List<Post>> {
            override fun onSuccess(posts: List<Post>) {
                _data.value = (FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: ApiError) {
                _data.value = FeedModel(errorVisible = true, error = e)

            }
        })
    }

    fun save() {
        edited.value?.let {
            repository.save(it, object : PostRepository.Callback<Post> {
                override fun onSuccess(posts: Post) {
                   _postCreated.value = Unit
                }

                override fun onError(e: ApiError) {
                    _postCreateError.value = e
                }

            })
        }
    }

    fun edit(post: Post) {
        edited.value = post
        loodPost()
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
        repository.likeById(id, object : PostRepository.Callback<Post> {
            override fun onSuccess(posts: Post) {
                _postCreated.value = Unit
                }

            override fun onError(e: ApiError) {
                _postCreateError.value = e
            }
        })
    }

    fun removeById(id: Long) {
        repository.removeById(id, object : PostRepository.Callback<Unit> {
            override fun onSuccess(post: Unit) {
                loodPost()
            }

            override fun onError(e: ApiError) {
               _postCreateError.value = e
            }
        })
    }
    fun disLikeById(id: Long) {
        repository.dislikeById(id, object : PostRepository.Callback<Post> {
            override fun onSuccess(posts: Post) {
                _postCreated.value = Unit
                loodPost()
            }

            override fun onError(e: ApiError) {
                _postCreateError.value = e
            }
        })
    }

    fun getPost(id: Long) = repository.getPost(id)
}