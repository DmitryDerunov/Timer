package com.example.timer.presentation

import kotlin.time.Duration

data class TimerState(
    val startTime: Int = 0,
    val remainingTime: Int = 0,
    val isRunning: Boolean = false,
    val timerDurationsInSeconds: List<Int> = emptyList()
)