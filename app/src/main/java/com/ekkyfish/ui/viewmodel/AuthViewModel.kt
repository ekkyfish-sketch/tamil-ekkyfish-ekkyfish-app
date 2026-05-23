package com.ekkyfish.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekkyfish.data.repository.AuthRepository
import com.ekkyfish.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(authRepository.isUserLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        _currentUser.value = authRepository.getCurrentUser()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = authRepository.login(email, password)
                result.onSuccess { user ->
                    _currentUser.value = user
                    _isLoggedIn.value = true
                }
                result.onFailure { exception ->
                    _error.value = exception.message ?: "Login failed"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signup(email: String, password: String, name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = authRepository.signup(email, password, name)
                result.onSuccess { user ->
                    _currentUser.value = user
                    _isLoggedIn.value = true
                }
                result.onFailure { exception ->
                    _error.value = exception.message ?: "Signup failed"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _currentUser.value = null
        _isLoggedIn.value = false
    }
}