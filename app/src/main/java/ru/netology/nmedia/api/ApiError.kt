package ru.netology.nmedia.api

import java.io.IOException
import java.sql.SQLException

//sealed class ApiError {
//    object ServerError : ApiError()
//    object NetworkError : ApiError()
//    object UnknownError : ApiError()
//
//    companion object {
//        fun fromThrowable(throwable: Throwable): ApiError =
//            when (throwable) {
//                is ApiException -> throwable.error
//                is ConnectException -> NetworkError
//                else -> UnknownError
//            }
//    }
//
//}
//
//fun ApiError?.getHumanReadableMessage(resources: Resources): String =
//    when(this){
//        ApiError.ServerError -> resources.getString(R.string.server_error)
//        ApiError.NetworkError -> resources.getString(R.string.network_error)
//        ApiError.UnknownError, null -> resources.getString(R.string.unknown_error)
//    }
sealed class AppError(var code: String): RuntimeException() {
    companion object {
        fun from(e: Throwable): AppError = when(e){
            is AppError -> e
            is SQLException -> DbError
            is IOException -> NetworkError
            else -> UnknownError
        }
    }
}
class ApiError(val status: Int, code: String) : AppError(code)
    object NetworkError : AppError("error_network")
    object UnknownError : AppError("error_unknown")
    object DbError : AppError("error_db")
