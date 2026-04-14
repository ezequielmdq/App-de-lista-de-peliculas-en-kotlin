package com.example.peliculaspopulares.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.peliculaspopulares.PeliculasApplication
import com.example.peliculaspopulares.repositorio.SessionData
import com.example.peliculaspopulares.repositorio.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserPreferencesViewModel(private val repository: UserPreferencesRepository) : ViewModel() {

    init {
        getSession()
    }

    private val _session = MutableStateFlow(SessionData(sesion = false, 0L))

    val session: StateFlow<SessionData> = _session.asStateFlow()

    fun getSession() {
        viewModelScope.launch {
            repository.session().collect {
                _session.value = it
            }
        }
    }

    fun login(email: Boolean) {
        viewModelScope.launch {
            repository.saveSession(email)
        }
    }


    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }


    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PeliculasApplication)
                val repository = application.container.userPreferencesRepository

                UserPreferencesViewModel(
                    repository = repository,
                )
            }
        }
    }
}


