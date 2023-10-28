package com.example.simplechat.response

data class ViewResponse(
    val responseType: ResponseType,
    val message: String?,
) {
    companion object {
        fun success(message: String?): ViewResponse {
            return ViewResponse(responseType = ResponseType.SUCCESS, message = message)
        }

        fun error(message: String?): ViewResponse {
            return ViewResponse(responseType = ResponseType.ERROR, message = message)
        }

        fun loading(): ViewResponse {
            return ViewResponse(responseType = ResponseType.LOADING, message = null)
        }
    }
}