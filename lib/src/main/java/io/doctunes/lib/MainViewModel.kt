package io.doctunes.lib

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
): ViewModel() {

    fun sync() {
        viewModelScope.launch {
            repository.syncStrings()
        }
    }
}