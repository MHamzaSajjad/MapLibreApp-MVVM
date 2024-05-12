package com.mhamzasajjad.mapapp.data.utils

class ErrorThrowable(
    val errorCode: Int? = 0,
    val errorMessage: String? = null,
    val errorTitle: String? = null
) : Throwable(errorMessage)