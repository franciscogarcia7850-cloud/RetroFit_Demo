package com.example.retrofit_demo.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit_demo.data.Post
import com.example.retrofit_demo.network.RetrofitClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PostUiState {
    data object Loading : PostUiState
    data object Error : PostUiState
    data class Success(val posts: List<Post>) : PostUiState
}

class PostViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<PostUiState>(PostUiState.Loading)
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = PostUiState.Loading
            _uiState.value = try {
                PostUiState.Success(RetrofitClient.instance.getPosts())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Sin conexión, error HTTP o respuesta con formato inesperado
                Log.e("PostViewModel", "Error al consultar los posts", e)
                PostUiState.Error
            }
        }
    }
}
