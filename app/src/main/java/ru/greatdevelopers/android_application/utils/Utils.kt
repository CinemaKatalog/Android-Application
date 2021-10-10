package ru.greatdevelopers.android_application.utils

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import retrofit2.Response
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.network.Result
import java.io.IOException

object Utils {

    const val PASSWORD_PATTERN = "^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=\\S+$)" +           //no white spaces
            ".{8,}" +               //at least 8 characters
            "$"

    const val EMAIL_PATTERN = "^" +
            "([a-z0-9_\\.-]+)" +    //several letters a-z, digits, _, - and .
            "@" +                   //@
            "([a-z0-9_\\.-]+)" +    //several letters a-z, digits, _, - and .
            "\\." +                 //.
            "([a-z]{2,6}\\.?)" +    //domain zone (several letters a-z repeats 2-6 times and . )
            "$"

    const val URL_PATTERN = "^" +
            "(https?:\\/\\/)?" +    //http, then s or not, then ://, however it may be missed
            "([\\da-z\\.-]+)" +     //several letters a-z, digits, - and .
            "\\." +                 // .
            "([a-z]{2,6}\\.?)" +    //domain zone (several letters a-z repeats 2-6 times and . )
            "([\\/\\w\\.-]*)*\\/?" +//slash and any alphabet symbols with . and - repeats from 0 to infinity times, and / or not
            "$"


    fun showToast(context: Context, text: String, duration: Int) {
        val toast = Toast.makeText(context, text, duration)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.view.background.setColorFilter(
            context.getColor(R.color.color_background_dark),
            PorterDuff.Mode.SRC_IN
        )
        toast.view.findViewById<TextView>(android.R.id.message)
            .setTextColor(context.getColor(R.color.color_primary))
        toast.show()
    }


    suspend fun <T> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): T? {
        val result: Result<T> =
            safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is Result.Success -> data = result.data
            is Result.Error -> {
                Log.d("1.Repository", "$errorMessage & Exception - ${result.exception}")
            }
        }

        return data
    }

    private suspend fun <T> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        val response = call.invoke()
        return if (response.isSuccessful) //Result.Success(response.body()!!)
        // response.body()?.let { Result.Success(response.body()!!) } ?: Result.Error(UnknownError)
            response.body()?.let { Result.Success(response.body()!!) }
                ?: Result.Error(IOException("Error occurred during getting Api result, Custom ERROR - $errorMessage ${response.code()}"))
        else Result.Error(IOException("Error occurred during getting Api result, Custom ERROR - $errorMessage ${response.code()}"))
        /*Result.Error(
        exception = when (response.code()) {
            304 -> NotModifiedError
            400 -> InvalidRequestDataError
            401 -> UnauthorizedError
            403 -> ForbiddenError
            404 -> NotFoundError
            500 -> ServerError
            else -> UnknownError
        }
    )*/
    }
}


/*
internal inline fun <T, R> Api.call(
    call: Api.() -> Response<T>,
    onSuccess: (T) -> R,
): Result<R> = try {
    val response: Response<T> = call()
    if (response.isSuccessful) {
        response.body()?.let { Success(onSuccess(it)) } ?: Failure(UnknownError)
    } else {
        Failure(
            reason = when (response.code()) {
                304 -> NotModifiedError
                400 -> InvalidRequestDataError
                401 -> UnauthorizedError
                403 -> ForbiddenError
                404 -> NotFoundError
                500 -> ServerError
                else -> UnknownError
            }
        )
    }
} catch (e: Exception) {
    logE(e)
    Failure(
        if (e is IOException) {
            ConnectionError
        } else {
            UnknownError
        }
    )
}.also {
    if (it is Failure) logE(it.reason.toString())
}


sealed class Result<out T>

data class Success<out T>(val value: T) : Result<T>() {
    companion object {
        val unit: Success<Unit> get() = Success(Unit)
    }
}

data class Error(val reason: AppError) : Result<Nothing>()

val <T> Result<T>.valueOrNull: T?
    get() = when (this) {
        is Success -> value
        is Error -> null
    }
val <T, R : List<T» Result<R>.valueOrEmpty: List<T>
    get() = when (this) {
        is Success -> value
        is Error -> emptyList()
    }

val <T> Result<T>.reasonOrNull: AppError?
    get() = (this as? Error)?.reason

inline fun <T, R> Result<T>.map(
    onSuccess: (T) -> Result<R>,
    onFailure: (AppError) -> Result<R>,
): Result<R> = when (this) {
    is Success -> onSuccess(value)
    is Error -> onFailure(reason)
}

inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> = apply {
    if (this is Success) block(value)
}

inline fun <T, R> Result<T>.mapSuccess(block: (T) -> Result<R>): Result<R> = when (this) {
    is Success -> block(value)
    is Error -> this
}

inline fun <T> Result<T>.mapFailure(block: (AppError) -> Result<T>): Result<T> = when (this) {
    is Success -> this
    is Error -> block(reason)
}

fun <T> Result<T?>.mapNullToFailure(error: AppError? = null): Result<T> = when (this) {
    is Success -> if (value != null) Success(value) else Failure(error ?: UnknownError)
    is Error -> this
}*/
