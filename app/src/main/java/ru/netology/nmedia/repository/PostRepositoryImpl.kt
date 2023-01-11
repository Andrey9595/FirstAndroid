package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.ApiError
import ru.netology.nmedia.api.PostApiServiceHolder
import ru.netology.nmedia.dto.Post


class PostRepositoryImpl : PostRepository {

    override fun getAllAsync(callback: PostRepository.Callback<List<Post>>) {
        PostApiServiceHolder.service.getAll()
            .enqueue(object : Callback<List<Post>>{
                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    callback.onSuccess(response.body().orEmpty())
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    callback.onError(ApiError.fromThrowable(t))
                }

            })
    }

    override fun save(post: Post, callback: PostRepository.Callback<Post>) {
        PostApiServiceHolder.service.save(post)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(ApiError.fromThrowable(t))
                }
            })
    }

    override fun removeById(id: Long, callback: PostRepository.Callback<Unit>) {
        PostApiServiceHolder.service.removeById(id)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    callback.onSuccess(response.body() ?: Unit)
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    callback.onError(ApiError.fromThrowable(t))
                }
            })
    }

    override fun dislikeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostApiServiceHolder.service.dislikeById(id)
            .enqueue(object : Callback<Post>{
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(ApiError.fromThrowable(t))
                }

            })
    }

    override fun likeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostApiServiceHolder.service.likeById(id)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(ApiError.fromThrowable(t))
                }
            })
    }

    override fun getPost(id: Long): Post {
        TODO("Not yet implemented")
    }
}
