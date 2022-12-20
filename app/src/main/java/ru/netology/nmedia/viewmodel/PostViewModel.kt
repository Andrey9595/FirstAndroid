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
    likedByMe = false,
    published = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data

    init {
        loodPost()
    }

    fun loodPost() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.GetAllCallback {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    val edited = MutableLiveData(empty)
    val _postCreated = SingleLiveEvent<Unit>()

    fun save() {
        edited.value?.let {
            repository.saveAsync(it, object : PostRepository.GetAllCallback {
                override fun onSuccess(posts: List<Post>) {
                    _postCreated.postValue(Unit)
                }

                override fun onError(e: Exception) {
                    _data.postValue(FeedModel(error = true))
                }

            })
        }
//            thread {
//                try {
//                    repository.save(it)
//                    _postCreated.postValue(Unit)
//                } catch (e: IOException) {
////
//                }
//            }
//        }
//        edited.value = empty
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
repository.likeByIdAsync(id, object : PostRepository.GetAllCallback{
    override fun onSuccess(posts: List<Post>) {
        updatedPost = updatedPost.map { post ->
            if (post.id != id) post else post.copy(
                likedByMe = post.likedByMe,
                likes = post.likes
            )
        }
        _data.postValue(FeedModel(posts = updatedPost))
    }

    override fun onError(e: Exception) {
        _data.postValue(FeedModel(error = true))
    }
})
//        thread {
//            val updatedPost = repository.likeByIdAsync(id)
//            val posts = _data.value?.posts.orEmpty()
//                .map { if (it.id == id) updatedPost else it }
//
//            _data.postValue(FeedModel(posts = posts))
//        }
    }

    fun removeById(id: Long) {
        repository.removeByIdAsync(id, object: PostRepository.GetAllCallback{
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .filter { it.id != id }
                    )
                )
            }
            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })


//        thread {
//            val old = _data.value?.posts.orEmpty()
//            _data.postValue(_data.value?.copy(posts = _data.value?.posts.orEmpty()
//                .filter { it.id != id }
//            )
//            )
//            try {
//                repository.removeById(id)
//            } catch (e: IOException) {
//                _data.postValue(_data.value?.copy(posts = old))
//            }
//        }
    }

    fun shareById(id: Long) = repository.shareById(id)
    fun getPost(id: Long) = repository.getPost(id)
}