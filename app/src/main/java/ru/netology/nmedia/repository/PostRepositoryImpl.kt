package ru.netology.nmedia.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import ru.netology.nmedia.api.ApiError
import ru.netology.nmedia.api.AppError
import ru.netology.nmedia.api.NetworkError
import ru.netology.nmedia.api.UnknownError
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.entity.toDto
import ru.netology.nmedia.entity.toEntity
import java.io.IOException


class PostRepositoryImpl(private val dao: PostDao) : PostRepository {
    override val data = dao.getAll()
        .map(List<PostEntity>:: toDto)
        .flowOn(Dispatchers.Default)

    override fun getNewerCount(id: Long): Flow<Int> = flow {
        while (true)
            delay(10_000L)
        val response = PostApi.service.getNewer(id)
        if (!response.isSuccessful){
            throw ApiError(response.code(), response.message())
        }
        val  body = response.body()?:  throw ApiError(response.code(), response.message())
        for (i in body){
            i.toShow = false
        }
        emit(body.size)
    }
        .catch { e -> throw AppError.from(e) }
        .flowOn(Dispatchers.Default)

    override suspend fun getAll() {
        try {
            val response = PostApi.service.getAll()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(
                response.code(),
                response.message()
            )
          for (i in body){
              i.toShow = true
          }
            dao.insert(body.toEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun save(post: Post) {
        val tempId = dao.insert(PostEntity.fromDto(post))
        try {
            val response = PostApi.service.save(post)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body =
                response.body() ?: throw ApiError(response.code(), response.message())
//            body.savedOnServer = true
//            dao.removeById(tempId)
            dao.insert(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw  NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun removeById(id: Long) {
        dao.removeById(id)
        try {
            val response = PostApi.service.removeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }

    }

    override suspend fun likeById(id: Long) {
        dao.likeById(id)
        try {
            val response = PostApi.service.likeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body =
                response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun dislikeById(id: Long) {
        dao.likeById(id)
        try {
            val response = PostApi.service.dislikeById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body =
                response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(PostEntity.fromDto(body))

        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun updateShownStatus() {
        TODO("Not yet implemented")
    }

}
