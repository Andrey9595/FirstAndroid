package ru.netology.nmedia.model

import ru.netology.nmedia.api.ApiError
import ru.netology.nmedia.dto.Post

data class FeedModel(
    val posts: List<Post> = emptyList(),
    val loading: Boolean = false,
    val errorVisible: Boolean = false,
    val error: ApiError? = null,
    val empty: Boolean = false,
    val refreshing: Boolean = false,
)
