package com.example.timer.di

import com.example.timer.presentation.TimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<TimerViewModel> { TimerViewModel() }
}