package com.example.hw2

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.hw2.ui.theme.HW2Theme
import java.time.LocalDate
import java.time.YearMonth

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HW2Theme {
                HW2App()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@PreviewScreenSizes
@Composable
fun HW2App() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title= {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.teal_200), // Background color
                    titleContentColor = MaterialTheme.colorScheme.primary // Text color
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ){
            Text_View(
                modifier = Modifier
            )
            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Landscape: Side-by-side
                Row {
                    AppCalculator(Modifier.fillMaxWidth(.5f))
                    AppCalendar(LocalDate.now(), Modifier.fillMaxWidth())
                }
            } else {
                // Portrait: Stacked
                AppCalculator(Modifier.aspectRatio(1f))
                AppCalendar(LocalDate.now())
            }
        }
    }
}

@Composable
fun Text_View(modifier: Modifier = Modifier){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = Color.Gray,
                    strokeWidth = 2.dp.toPx(),
                    start = Offset(0.0f, size.height),
                    end = Offset(size.width, size.height)
                )
            },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = stringResource(R.string.txt_view),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .fillMaxWidth()
                .padding(2.dp)
        )
    }
}

@OptIn(ExperimentalGridApi::class)
@Composable
fun AppCalculator(modifier: Modifier = Modifier) {
    val keys = stringArrayResource(R.array.calc_keys)
    Box (
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Grid (
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            config = {repeat(4) { column(4.fr)} } // 4 columns each 1/4 of width
        ){
            OutlinedTextField(
                value="",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .border(2.dp, MaterialTheme.colorScheme.primary)
                    .gridItem(row = 1, column = 1, columnSpan = 4)
                    .fillMaxWidth()
            )
            keys.forEachIndexed { index, key ->
                val spanModifier = when (index) {
                    15 -> {
                        Modifier.gridItem(row = 5, column = 4, rowSpan = 3).fillMaxHeight()
                    }
                    16 -> {
                        Modifier.gridItem(row = 6, column = 1, columnSpan = 2)
                    }
                    else -> {
                        Modifier
                    }
                }

                Box(
                    modifier = Modifier
                        .then(spanModifier)
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        text = key,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDaysOfMonth(yearMonth: YearMonth): List<LocalDate?> {
    val dayOfWeekForFirstOfMonth = yearMonth.atDay(1).dayOfWeek.value
    val days = mutableListOf<LocalDate?>()

    for (i in 1 until dayOfWeekForFirstOfMonth) {
        days.add(null)
    }

    // fill days up to 1st day of month with null
    for (day in 1..yearMonth.lengthOfMonth()) {
        days.add(yearMonth.atDay(day))
    }

    return days
}

@OptIn(ExperimentalGridApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppCalendar(localDate: LocalDate, modifier: Modifier = Modifier) {
    val days: List<LocalDate?> = getDaysOfMonth(YearMonth.from(localDate))
    val weekDays = stringArrayResource(R.array.weekDays)
    val calTitle = arrayOf("<", YearMonth.from(localDate).toString(), ">")

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Calendar Grid Layout
        Grid(
            modifier = Modifier
                .fillMaxWidth(),
            config = { repeat(7) { column(7.fr) } } // 7 columns each 1/7 of width
        ) {
            calTitle.forEachIndexed { index, text ->
                val spanModifier = if (index == 1) {
                    Modifier.gridItem(columnSpan = 5)
                } else {
                    Modifier
                }
                Box(
                    modifier = Modifier
                        .then(spanModifier)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = text,
                        style = MaterialTheme.typography.titleMedium.copy(
                            lineHeight = MaterialTheme.typography.titleMedium.fontSize // Match font size and line height
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            weekDays.forEach { day ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center,
                        text = day,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            days.forEach { date ->
                DayCell(date = date, isSelected = date == localDate)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCell(date: LocalDate?, isSelected: Boolean) {
    Box {
        if (date != null) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = CircleShape
                    ),
                textAlign = TextAlign.Center,
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

