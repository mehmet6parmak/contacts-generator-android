package com.teknolojipiri.contactsgenerator.generate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactGeneratorViewModel @Inject constructor(private val generateContactsUseCase: GenerateContactsUseCase) : ViewModel() {

    private val _operationResult = MutableSharedFlow<Result>()
    val operationResult = _operationResult
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Result.Success()
        )

    fun generateContacts(prefix: String, rangeStart: String, rangeEnd: String) {
        viewModelScope.launch {
            try {
                _operationResult.emit(Result.InProgress())
                generateContactsUseCase.generateContacts(prefix, rangeStart, rangeEnd)
                _operationResult.emit(Result.Success())
            } catch (e: Exception) {
                e.printStackTrace()
                _operationResult.emit(Result.Failed())
            }
        }
    }

    sealed class Result(val buttonEnabled: Boolean) {
        class Success : Result(true)
        class InProgress : Result(false)
        class Failed : Result(true)
    }
}