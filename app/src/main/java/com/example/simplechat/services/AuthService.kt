package com.example.simplechat.services

import android.util.Log
import com.example.simplechat.data.api.AuthApi
import com.example.simplechat.data.api.RetrofitClient
import com.example.simplechat.data.models.User
import com.example.simplechat.data.models.response.BaseResponse
import com.example.simplechat.data.models.response.LoginResponse
import com.example.simplechat.data.repositories.AuthRepository
import com.example.simplechat.response.CustomException
import com.example.simplechat.response.FlowResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit

class AuthService(
    private val authRepository: AuthRepository,
) {
    private val retrofit: Retrofit = RetrofitClient.getClient()
    private val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    val gson : Gson = GsonBuilder().create()

    suspend fun login(email: String, password: String) = channelFlow<FlowResponse> {
        val body = mapOf(
            "email" to email,
            "password" to password
        )
        launch {
            val response = authApi.login(body).execute()
            launch {
                if (response.isSuccessful) {
                    authRepository.saveUser(response.body()!!.data!!)
                    send(
                        FlowResponse.Success("Successful ${response.body()}")
                    )
                } else {
                    var obj = gson.fromJson(response.errorBody()!!.string().toString(), BaseResponse::class.java)
                    send(
                        FlowResponse.Error(
                            obj.message.toString(),
                            CustomException.HttpException
                        )
                    )
                }
            }

        }
    }.flowOn(Dispatchers.IO).catch {
        it.printStackTrace()
        emit(
            FlowResponse.Error("Error - Something went wrong", CustomException.SomethingWentWrong)
        )
    }

    suspend fun signUp(firstName: String, lastName: String, email: String, password: String) =
        channelFlow<FlowResponse> {
            val body = mapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "email" to email,
                "password" to password,
            )
            launch {
                val response = authApi.signUp(body).execute()
                launch {
                    if (response.isSuccessful) {
                        Log.d("AuthService", "[SignUp] Response = ${response.body()}")
                        send(
                            FlowResponse.Success("Successfully created your account, log in to continue")
                        )
                    } else {
                        send(
                            FlowResponse.Error(
                                "Error ${response.body()}",
                                CustomException.HttpException
                            )
                        )
                    }
                }
            }
        }

    suspend fun getCurrentUser(): User? {
        return authRepository.getCurrentUser()
    }
}