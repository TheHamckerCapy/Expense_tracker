package com.example.expense_tracker.feature.stats

import android.view.LayoutInflater
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expense_tracker.R
import com.example.expense_tracker.Utils
import com.example.expense_tracker.feature.home.TransactionList
import com.example.expense_tracker.widgets.ExpenseTextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(navController: NavController) {
    var selectedType by remember { mutableStateOf("Expense") }
    var dropdownexpanded by remember { mutableStateOf(false) }
    val dropdownOptions = listOf("Expense", "Income")
    Scaffold(
        topBar = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .clickable { navController.popBackStack() },
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
                    )
                    ExpenseTextView(
                        text = "Statistics",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center)
                    )
                    Image(
                        painter = painterResource(R.drawable.dots_menu),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterEnd),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = dropdownexpanded,
                        onExpandedChange = { dropdownexpanded = !dropdownexpanded },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        OutlinedTextField(
                            value = selectedType,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownexpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .width(140.dp)
                                .height(56.dp),
                            shape = RoundedCornerShape(28.dp),
                            textStyle = TextStyle(fontSize = 14.sp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.Gray.copy(alpha = 0.6f),
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = dropdownexpanded,
                            onDismissRequest = { dropdownexpanded = false },
                            modifier = Modifier
                                .width(140.dp)
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    1.dp,
                                    Color.Gray.copy(alpha = 0.3f),
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            dropdownOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = {
                                        ExpenseTextView(
                                            text = option, fontSize = 14.sp,
                                            color = if (option == selectedType)
                                                MaterialTheme.colorScheme.primary
                                            else
                                                Color.Black
                                        )
                                    },
                                    onClick = {
                                        selectedType = option
                                        dropdownexpanded = false

                                    }
                                )

                            }
                        }
                    }
                }
            }
        }
    ) {
        val viewModel =
            StatsViewModelFactory(navController.context).create(StatsViewModel::class.java)
        val dataStats = viewModel.entries.collectAsState(emptyList())
        val topExpenses = viewModel.topEntries.collectAsState(emptyList())
        val incomeDatasets = viewModel.incomeEntries.collectAsState(emptyList())
        val topIncome = viewModel.topIncome.collectAsState(emptyList())

        val currentData = if (selectedType == "Income") {
            incomeDatasets.value
        } else {
            dataStats.value
        }
        val currentTopList = if (selectedType == "Income") {
            topIncome.value
        } else {
            topExpenses.value
        }
        Column(
            modifier = Modifier.padding(it)
        ) {

            val entries = viewModel.getEntriesForChart(currentData)
            LineChart(entries = entries)
            Spacer(modifier = Modifier.height(16.dp))
            TransactionList(modifier = Modifier, list = currentTopList, "Top $selectedType ")
        }
    }
}

@Composable
fun LineChart(entries: List<Entry>) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            val view = LayoutInflater.from(context).inflate(R.layout.stats_line_chart, null)
            view
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
    ) { view ->

        val lineChart = view.findViewById<LineChart>(R.id.lineChart)
        val dataSets = LineDataSet(entries, "Expense").apply {
            color = android.graphics.Color.parseColor("#FF2F7E79")
            valueTextColor = android.graphics.Color.BLACK
            lineWidth = 3f
            axisDependency = YAxis.AxisDependency.RIGHT
            setDrawFilled(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            cubicIntensity = 0.2f

            valueTextSize = 12f
            valueTextColor = android.graphics.Color.parseColor("#FF2F7E79")
            val drawable = ContextCompat.getDrawable(context, R.drawable.char_gradient)
            drawable?.let {
                fillDrawable = drawable
            }
            setDrawHighlightIndicators(false)

        }
        lineChart.data = LineData(dataSets)
        lineChart.xAxis.valueFormatter =
            object : com.github.mikephil.charting.formatter.ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return Utils.formatDateForChart(value.toLong())
                }
            }
        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.setDrawAxisLine(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.apply {
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false

            setBackgroundColor(android.graphics.Color.TRANSPARENT)
            setGridBackgroundColor(android.graphics.Color.TRANSPARENT)
        }
        lineChart.invalidate()
    }
}

@Preview
@Composable
private fun StatisticsScreenPreview() {
    StatsScreen(rememberNavController())
}