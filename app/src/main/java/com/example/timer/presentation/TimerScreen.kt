package com.example.timer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.timer.R
import com.example.timer.ui.theme.GrayBlue100
import com.example.timer.ui.theme.GrayBlue70
import org.koin.androidx.compose.getViewModel

@Composable
fun TimerScreen() {

    val timerViewModel = getViewModel<TimerViewModel>()
    val state by timerViewModel.timerState.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED)

    ComposableLifecycle { source, event ->
        when(event){
            Lifecycle.Event.ON_PAUSE -> timerViewModel.pauseTimer()
            Lifecycle.Event.ON_RESUME -> timerViewModel.continueTimer()
            else -> {}
        }
    }
    Timer(state, timerViewModel::toggleTimer, timerViewModel::setStartTime)
}

@Composable
fun Timer(state: TimerState, onToggleTimerButtonCLick: () -> Unit, onStarTimeSelected: (selectedTime: Int) -> Unit){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = ImageVector.vectorResource(R.drawable.background),
            contentScale = ContentScale.Crop,
            contentDescription = "background")
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {


            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = if (state.isRunning) "Таймер установлен" else "Установите таймер",
                fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                fontSize = 22.sp,
                color = GrayBlue100)

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Время закончится и цыпа ляжет спать",
                fontFamily = FontFamily(Font(R.font.open_sans_regular)),
                fontSize = 12.sp,
                color = GrayBlue70)

            Spacer(modifier = Modifier
                .height(40.dp)
                .fillMaxWidth())

            Box{
                TimerView(state)
                if(!state.isRunning)
                {
                    WheelTimePicker(
                        modifier = Modifier.align(Alignment.Center),
                        sourceItems = state.timerDurationsInSeconds,
                        onItemSelected = onStarTimeSelected,
                        startIndex = state.timerDurationsInSeconds.indexOf(state.startTime))
                }
            }


            Spacer(modifier = Modifier
                .height(76.dp)
                .fillMaxWidth())

            Button(
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        spotColor = Color(0x33C93936),
                        ambientColor = Color(0x33C93936)
                    )
                    .height(48.dp)
                    .padding(0.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor =
                if(state.isRunning) Color(0xFF00A1E7) else Color(0xFFEB4D4A)),
                onClick = { onToggleTimerButtonCLick() }) {

                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(
                        if(state.isRunning) R.drawable.stop_icon else R.drawable.start_icon), contentDescription = "start button")

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = if (state.isRunning) "стоп" else "старт",
                    fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                    fontSize = 16.sp)
            }
        }

    }
}


@Preview
@Composable
fun TimerScreenTest(){
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        Column(
            modifier = Modifier.fillMaxSize().scrollable(rememberScrollState(), orientation = Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Установите таймер",
                fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                fontSize = 22.sp)

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Время закончится и цыпа ляжет спать",
                fontFamily = FontFamily(Font(R.font.open_sans_regular)),
                fontSize = 12.sp)

            Spacer(modifier = Modifier
                .height(40.dp)
                .fillMaxWidth())

            Box{
                TimerView(TimerState())
                WheelTimePicker(
                    modifier = Modifier.align(Alignment.Center),
                    sourceItems = listOf(10, 20, 30),
                    onItemSelected = {})
            }


            Spacer(modifier = Modifier
                .height(76.dp)
                .fillMaxWidth())

            Button(
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        spotColor = Color(0x33C93936),
                        ambientColor = Color(0x33C93936)
                    )
                    .height(48.dp)
                    .padding(0.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC93936)),
                onClick = { /*TODO*/ }) {

                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.start_icon), contentDescription = "start button")

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "старт",
                    fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                    fontSize = 16.sp)
            }
        }
    }
}


@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
){
    DisposableEffect(lifecycleOwner){
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}