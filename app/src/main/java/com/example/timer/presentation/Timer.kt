package com.example.timer.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.R
import com.example.timer.secondsToFormattedTime
import com.example.timer.ui.theme.GrayBlue100
import com.example.timer.ui.theme.GrayBlue70
import com.example.timer.ui.theme.timerIndicatorColor

@Composable
fun TimerView(state: TimerState) {
    Box(){
        Image(
            modifier = Modifier.size(width = 320.dp, height = 260.dp),
            imageVector = ImageVector.vectorResource(R.drawable.timer),
            contentDescription = "timer image")
        timerIndicator(Modifier.padding(top = 36.dp, start = 64.dp), state)
        if(state.isRunning)
        {
            Text(
                text = "Осталось",
                fontFamily = FontFamily(Font(R.font.open_sans_regular)),
                modifier = Modifier.align(Alignment.Center).padding(bottom = 80.dp),
                fontSize = 16.sp,
                color = GrayBlue70)
            Text(
                text = state.remainingTime.secondsToFormattedTime(),
                modifier = Modifier.align(Alignment.Center),
                fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                fontSize = 52.sp,
                color = GrayBlue100)
        }
    }
}


@Composable
fun timerIndicator(modifier: Modifier = Modifier, state: TimerState){
    Canvas(modifier = modifier.size(192.dp, 192.dp)){

        var percentage = 0f

        if(state.remainingTime <= state.startTime && state.startTime != 0){
            percentage = (state.remainingTime.toFloat() / state.startTime.toFloat())
        }

        drawArc(
            color = timerIndicatorColor,
            startAngle = -90f,
            size = size,
            sweepAngle = -360 * percentage,
            useCenter = true,
        )
    }
}