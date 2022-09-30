package com.teknolojipiri.contactsgenerator.remove

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teknolojipiri.contactsgenerator.generate.GenerateContactsUseCase
import contacts.async.commitWithContext
import contacts.core.Contacts
import contacts.core.`in`
import contacts.core.isNotNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactRemoverViewModel @Inject constructor(private val context: Context) : ViewModel() {

    private val _uiState = MutableSharedFlow<UiState>()
    val uiState = _uiState.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), UiState.Success
    )

    fun removeAll() {
        viewModelScope.launch {
            try {
                _uiState.emit(UiState.InProgress)
                Contacts(context).delete().rawContactsWhereData { Phone.Number.isNotNull() }.commitWithContext()
                _uiState.emit(UiState.Success)
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.emit(UiState.Success)
            }
        }
    }

    fun deleteContacts(prefix: String, rangeStart: String, rangeEnd: String) {
        viewModelScope.launch {
            try {
                _uiState.emit(UiState.InProgress)
                withContext(Dispatchers.Default) {
                    val numbers = generateNumbers(prefix, rangeStart, rangeEnd)

                    Contacts(context).delete()
                        .rawContactsWhereData {
                            Phone.Number.`in`(numbers)
                        }
                        .commitWithContext()
                }
                _uiState.emit(UiState.Success)
            } catch (invalidInput: GenerateContactsUseCase.ContactGenerationError.InvalidInputs) {
                _uiState.emit(UiState.Failure("Please check input values."))
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.emit(UiState.Failure())
            }
        }
    }

    private fun generateNumbers(prefix: String, rangeStart: String, rangeEnd: String): List<String> {
        try {
            val start = rangeStart.toDouble().toLong()
            val end = rangeEnd.toDouble().toLong()

            val result = mutableListOf<String>()
            for(suffix in start..end) {
                val number = "$prefix${suffix}"
                result.add(number)
            }
            return result
        } catch (numberFormatException: NumberFormatException) {
            throw GenerateContactsUseCase.ContactGenerationError.InvalidInputs
        }
    }

    sealed class UiState(val buttonEnabled: Boolean) {
        object InProgress : UiState(false)
        object Success : UiState(true)
        class Failure(val message: String? = null): UiState(true)
    }

}