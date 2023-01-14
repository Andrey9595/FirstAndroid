package ru.netology.nmedia.api

import java.io.IOException

class ApiException(val error : ApiError, throwable : Throwable? = null) : IOException(throwable)