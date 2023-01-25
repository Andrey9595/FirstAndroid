package ru.netology.nmedia.api

import java.io.IOException

class ApiException(val error : AppError, throwable : Throwable? = null) : IOException(throwable)