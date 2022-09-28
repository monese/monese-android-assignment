package com.monese.assignment.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monese.assignment.data.Response
import com.monese.assignment.data.model.Launch
import com.monese.assignment.data.repository.LaunchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val launchesRepository: LaunchesRepository
) : ViewModel() {

    private val _launches = MutableStateFlow<List<Launch>?>(null)
    val launches = _launches.asStateFlow()

    val scope = viewModelScope

    fun getLaunches(forceUpdate: Boolean = false) {
        scope.launch {
            launchesRepository.getLaunches(forceUpdate).let { result ->
                if (result is Response.Success) {
                    _launches.value = result.data
                }
            }
        }
    }
}