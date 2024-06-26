package com.mw.hol_github_frontend.screen.main.user

import androidx.lifecycle.ViewModel
import com.mw.hol_github_frontend.api.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val apiClient: ApiClient) : ViewModel() {
    val username = MutableStateFlow("username")

    suspend fun fetchUserData(): Boolean {
        val res = apiClient.user.me()
        if (!res.isSuccessful) {
            return false
        }
        username.value = res.body()!!.username
        return true
    }

    suspend fun signOut() {
        apiClient.user.signOut()
    }
}
