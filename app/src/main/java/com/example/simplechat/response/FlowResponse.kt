package com.example.simplechat.response

sealed class FlowResponse(
    val message: String
) {
    class Loading: FlowResponse("")
    class Success(message : String) : FlowResponse(message)
    class Error(message: String, customException: CustomException) : FlowResponse(message)
}