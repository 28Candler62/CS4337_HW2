package com.example.hw2

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        topBar = { CenterAlignedTopAppBar(title= { Text(stringResource(R.string.app_name)) }) }
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
            AppCalendar(LocalDate.now())
        }
    }
}

@Composable
fun Text_View(modifier: Modifier = Modifier){
    Text(
        text = stringResource(R.string.txt_view),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.fillMaxWidth()
    )
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
fun AppCalendar(localDate: LocalDate) {
    val days: List<LocalDate?> = getDaysOfMonth(YearMonth.from(localDate))
    val weekDays = stringArrayResource(R.array.weekDays)

    Column(
        modifier = Modifier
            .border(2.dp, Color.Black)
    ) {
        // Title
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = YearMonth.from(localDate).toString(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all=4.dp),
            )
        }
        // Calendar Grid Layout
        Grid (
            modifier = Modifier.fillMaxWidth(),
            config = {repeat(7) { column(7.fr)} }
        ){
            weekDays.forEach { day ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        text = day,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            days.forEach { date ->
                DayCell(date=date, isSelected = date == localDate)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCell(date: LocalDate?, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = CircleShape
            ),
    ) {
        if (date != null) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

