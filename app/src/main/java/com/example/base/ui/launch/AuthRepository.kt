package com.example.base.ui.launch

import com.example.base.api.ApiService
import com.example.base.base.BaseRepository
import com.example.base.di.IoDispatcher
import com.example.base.manager.UserManager
import com.example.base.models.BaseResult
import com.example.base.models.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val userManager: UserManager,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : BaseRepository(ioDispatcher) {
    suspend fun login(email: String, password: String): Flow<BaseResult<User>> =
        getResult { apiService.login(email, password) }.onEach { result ->
            if (result is BaseResult.Success) {
                userManager.store(result.data, result.data.token)
            }
        }
}