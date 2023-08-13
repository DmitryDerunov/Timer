package com.example.timer.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.R
import com.example.timer.secondsToFormattedTime
import com.example.timer.secondsToMinutes
import com.example.timer.ui.theme.GrayBlue30
import com.example.timer.ui.theme.timePickerColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    sourceItems: List<Int>,
    onItemSelected: (selectedTime: Int) -> Unit,
) {
    val rowCount = 3
    val width = 135.dp
    val height = 175.dp

    val lazyListState = rememberLazyListState(startIndex)
    val snapBehavior = rememberSnapFlingBehavior(lazyListState)
    val isScrollInProgress = lazyListState.isScrollInProgress


    LaunchedEffect(isScrollInProgress) {
        if(!isScrollInProgress) {
            onItemSelected(sourceItems[lazyListState.firstVisibleItemIndex])
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(width, height / rowCount),
        ) {
            Divider(modifier = Modifier
                .width(60.dp)
                .align(Alignment.TopCenter), color = GrayBlue30, thickness = 2.dp)
            Text(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = "мин",
                fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                fontSize = 16.sp,
                color = timePickerColor,
            )
            Divider(modifier = Modifier
                .width(60.dp)
                .align(Alignment.BottomCenter), color = GrayBlue30, thickness = 2.dp)
        }

        LazyColumn(
            modifier = Modifier
                .height(height)
                .width(width),
            state = lazyListState,
            contentPadding = PaddingValues(vertical = height / rowCount * ((rowCount - 1 )/ 2)),
            flingBehavior = snapBehavior
        ){
            items(sourceItems.size){ index ->

                Box(
                    modifier = Modifier
                        .height(height / rowCount)
                        .width(width),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = sourceItems[index].secondsToMinutes().toString(),
                        fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                        fontSize = 42.sp,
                        color = timePickerColor,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}