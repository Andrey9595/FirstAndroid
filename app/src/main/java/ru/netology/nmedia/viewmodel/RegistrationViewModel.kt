package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.netology.nmedia.api.PostApiService
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    postDao: PostDao,
    apiService: PostApiService,
    appAuth: AppAuth
) : ViewModel() {
    private val repository: PostRepository =
        PostRepositoryImpl(postDao, apiService, appAuth)

    private val _tokenReceived = SingleLiveEvent<Int>()
    val tokenReceived: LiveData<Int>
        get() = _tokenReceived

    fun registerUser(login: String, pass: String) {
        viewModelScope.launch {
            try {
                repository.authentication(login, pass)
                _tokenReceived.value = 0
            } catch (e: Exception) {
                _tokenReceived.value = -1
            }
        }
    }
}