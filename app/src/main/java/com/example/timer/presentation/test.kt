package com.example.timer.presentation

import android.os.CountDownTimer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

 suspend fun main(){



    CoroutineScope(Dispatchers.Default).launch {

        val countDownTimer = object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                println(millisUntilFinished)
            }

            override fun onFinish() {
                println("finsih")
            }

        }

    }.join()


}