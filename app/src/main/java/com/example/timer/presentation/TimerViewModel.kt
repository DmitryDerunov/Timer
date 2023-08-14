package com.example.timer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timer.timer.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class TimerViewModel : ViewModel(), KoinComponent {

    private val timerDurationsInSeconds = listOf(
        5 * 60,
        10 * 60,
        15 * 60,
        20 * 60,
        25 * 60,
        30 * 60,
        35 * 60,
        40 * 60,
        45 * 60,
        50 * 60,
        55 * 60,
        60 * 60
    )

    private val isRunning = MutableStateFlow<Boolean>(false)
    private val remainingTimeFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val startTimeFlow: MutableStateFlow<Int> =
        MutableStateFlow(timerDurationsInSeconds.first())

    private val timer: CountDownTimer by inject { parametersOf(viewModelScope) }


    val timerState = combine(
        isRunning,
        remainingTimeFlow,
        startTimeFlow
    ) { isRunning, remainingTime, startTime ->

        if (!isRunning) {
            TimerState(
                startTime = startTimeFlow.value,
                timerDurationsInSeconds = timerDurationsInSeconds
            )
        } else {
            TimerState(startTime, remainingTime, true, timerDurationsInSeconds)
        }

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        TimerState(
            startTime = startTimeFlow.value,
            timerDurationsInSeconds = timerDurationsInSeconds
        )
    )


    fun toggleTimer() {
        if (!isRunning.value) {
            startTimer()
        } else {
            timer.stop()
            isRunning.value = false
        }
    }

    fun setStartTime(startTime: Int) {
        startTimeFlow.value = startTime
    }

    private fun startTimer() {
        val startTime = if (isRunning.value) remainingTimeFlow.value else startTimeFlow.value

        isRunning.value = true
        timer.start(
            startTimerInSeconds = startTime,
            onTimerEnded = {
                isRunning.value = false
            },
            onTick = {
                remainingTimeFlow.value = it
            }
        )
    }

    fun continueTimerIfNeeded() {
        if (isRunning.value) {
            startTimer()
        }
    }

    fun stopTimer() {
        timer.stop()
    }
}