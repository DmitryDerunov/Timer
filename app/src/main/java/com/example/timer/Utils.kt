package com.example.timer

fun Int.secondsToFormattedTime(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}

fun Int.secondsToMinutes(): Int {
    return this / 60
}