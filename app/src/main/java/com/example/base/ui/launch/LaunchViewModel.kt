package com.example.base.ui.launch

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.base.base.BaseViewModel
import com.example.base.manager.UserManager
import com.example.base.models.BaseResult
import com.example.base.utils.safeLaunch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userManager: UserManager,
) : BaseViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    val isLogin = MutableStateFlow(userManager.isLogged())

    data class LoginUiState(
        val loading: Boolean = false,
        val success: Boolean = false,
        val error: String? = null
    )

    fun login(email: String, password: String) {
        viewModelScope.safeLaunch {
            block = {
                authRepository.login(email, password).collect { result ->
                    when (result) {
                        is BaseResult.Loading -> {
                            _loginUiState.value = LoginUiState(loading = true)
                        }
                        is BaseResult.Error -> {
                            _loginUiState.value = LoginUiState(error = result.errorData.text)
                        }
                        is BaseResult.Success -> {
                            Log.e("result", result.data.toString())
                            _loginUiState.value = LoginUiState(success = true)
                        }
                    }
                }
            }
        }
    }
}