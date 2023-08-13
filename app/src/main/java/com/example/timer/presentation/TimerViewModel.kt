package com.example.timer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private companion object {
        const val DEFAULT_TIME = 10
        const val DELAY_MS = 1000L
    }

    private val timerDurationsInSeconds = listOf(5 * 60, 10 * 60, 15 * 60, 20 * 60, 25 * 60, 30 * 60, 35 * 60, 40 * 60, 45 * 60, 50 * 60, 55 * 60, 60 * 60)
    private val isRunning = MutableStateFlow<Boolean>(false)
    private val remainingTimeFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val startTimeFlow: MutableStateFlow<Int> = MutableStateFlow(timerDurationsInSeconds.first())

    private var jobTimer: Job? = null

    val timerState = combine(isRunning, remainingTimeFlow, startTimeFlow){ isRunning, remainingTime, startTime ->

        if(!isRunning){
            TimerState(startTime = startTimeFlow.value, timerDurationsInSeconds = timerDurationsInSeconds)
        } else {
            TimerState(startTime, remainingTime, true, timerDurationsInSeconds)
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), TimerState(startTime = startTimeFlow.value, timerDurationsInSeconds = timerDurationsInSeconds))



    fun toggleTimer() {
        if (!isRunning.value) {
            startTimer()
        } else {
            jobTimer?.cancel()
            jobTimer = null
            isRunning.value = false
        }
    }

    fun setStartTime(startTime: Int){
        startTimeFlow.value = startTime
    }

    private fun startTimer() {
        jobTimer = viewModelScope.launch {

            val startTime = if(isRunning.value) remainingTimeFlow.value else startTimeFlow.value

            isRunning.emit(true)

            countdownFlow(startTime).collect {
                remainingTimeFlow.emit(it)
            }
            isRunning.emit(false)
            jobTimer?.cancel()
            jobTimer = null
        }
    }

    fun continueTimer() {
        if (isRunning.value) {
            startTimer()
        }
    }

    fun pauseTimer(){
        jobTimer?.cancel()
        jobTimer = null
    }


    private fun countdownFlow(time: Int): Flow<Int> = flow {
        for (i in time -1 downTo 0) {
            emit(i)
            delay(DELAY_MS)
        }
    }

}