package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.api.AppError
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.entity.toDto
import ru.netology.nmedia.entity.toEntity
import java.io.IOException


class PostRepositoryImpl(private val dao: PostDao) : PostRepository {
    override val data: LiveData<List<Post>>
        get() = dao.getAll().map(List<PostEntity>::toDto)

    override suspend fun getAll() {
        try {
            val response = PostApi.service.getAll()
            if (!response.isSuccessful) {
                throw AppError.ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw AppError.ApiError(
                response.code(),
                response.message()
            )
            body.map {
                it.savedOnServer = true
            }
            dao.insert(body.toEntity())
        } catch (e: IOException) {
            throw AppError.NetworkError
        } catch (e: Exception) {
            throw AppError.UnknownError
        }
    }

    override suspend fun save(post: Post) {
        val tempId = dao.insert(PostEntity.fromDto(post))
        try {
            val response = PostApi.service.save(post)
            if (!response.isSuccessful) {
                throw AppError.ApiError(response.code(), response.message())
            }
            val body =
                response.body() ?: throw AppError.ApiError(response.code(), response.message())
            body.savedOnServer = true
            dao.removeById(tempId)
            dao.insert(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw  AppError.NetworkError
        } catch (e: Exception) {
            throw AppError.UnknownError
        }
    }

    override suspend fun removeById(id: Long) {
        dao.removeById(id)
        try {
            val response = PostApi.service.removeById(id)
            if (!response.isSuccessful) {
                throw AppError.ApiError(response.code(), response.message())
            }
        } catch (e: IOException) {
            throw AppError.NetworkError
        } catch (e: Exception) {
            throw AppError.UnknownError
        }

    }

    override suspend fun likeById(id: Long) {
        dao.likeById(id)
        try {
            val response = PostApi.service.likeById(id)
            if (!response.isSuccessful) {
                throw AppError.ApiError(response.code(), response.message())
            }
            val body =
                response.body() ?: throw AppError.ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw AppError.NetworkError
        } catch (e: Exception) {
            throw AppError.UnknownError
        }
    }

    override suspend fun dislikeById(id: Long) {
        dao.likeById(id)
        try {
            val response = PostApi.service.dislikeById(id)
            if (!response.isSuccessful) {
                throw AppError.ApiError(response.code(), response.message())
            }
            val body =
                response.body() ?: throw AppError.ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))

        } catch (e: IOException) {
            throw AppError.NetworkError
        } catch (e: Exception) {
            throw AppError.UnknownError
        }
    }

}
