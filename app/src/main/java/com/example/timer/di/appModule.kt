package com.example.timer.di

import com.example.timer.presentation.TimerViewModel
import com.example.timer.timer.CountDownTimer
import com.example.timer.timer.CountDownTimerImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<TimerViewModel> { TimerViewModel() }

    factory<CountDownTimer> { params ->  CountDownTimerImpl(params.get()) }
}