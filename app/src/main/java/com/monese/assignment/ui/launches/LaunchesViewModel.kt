package com.monese.assignment.ui.launches

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monese.assignment.data.Response
import com.monese.assignment.data.model.Launch
import com.monese.assignment.data.repository.LaunchesRepository
import kotlinx.coroutines.launch

class LaunchesViewModel @ViewModelInject constructor(
    private val launchesRepository: LaunchesRepository
) : ViewModel() {

    private val _launches = MutableLiveData<List<Launch>>()
    val launches: LiveData<List<Launch>> = _launches

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