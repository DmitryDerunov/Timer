package com.example.timer.timer

import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.time.Duration


interface CountDownTimer {

    fun start(startTimerInSeconds: Int, onTimerEnded: () -> Unit, onTick: (Int) -> Unit)

    fun stop()
}


