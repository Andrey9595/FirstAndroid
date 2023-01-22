package ru.netology.nmedia.api

import java.io.IOException

class ApiException(val error : AppError.ApiError, throwable : Throwable? = null) : IOException(throwable)