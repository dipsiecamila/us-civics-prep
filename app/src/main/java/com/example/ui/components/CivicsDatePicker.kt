package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.util.AppStrings
import kotlinx.coroutines.launch

private data class MonthInfo(
    val number: Int,
    val nameEn: String,
    val nameEs: String
)

private val MONTHS = listOf(
    MonthInfo(1, "January", "Enero"),
    MonthInfo(2, "February", "Febrero"),
    MonthInfo(3, "March", "Marzo"),
    MonthInfo(4, "April", "Abril"),
    MonthInfo(5, "May", "Mayo"),
    MonthInfo(6, "June", "Junio"),
    MonthInfo(7, "July", "Julio"),
    MonthInfo(8, "August", "Agosto"),
    MonthInfo(9, "September", "Septiembre"),
    MonthInfo(10, "October", "Octubre"),
    MonthInfo(11, "November", "Noviembre"),
    MonthInfo(12, "December", "Diciembre")
)

private fun isLeapYear(year: Int): Boolean =
    (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

private fun maxDaysForMonth(month: Int, year: Int): Int {
    return when (month) {
        2 -> if (isLeapYear(year)) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }
}

@Composable
fun CivicsDatePicker(
    label: String,
    dateString: String,
    onDateChanged: (String) -> Unit,
    language: AppLanguage = AppLanguage.SPANISH,
    modifier: Modifier = Modifier
) {
    // Parse existing dateString
    var selectedMonth by remember(dateString) {
        val found = MONTHS.firstOrNull {
            dateString.contains(it.nameEn, ignoreCase = true) || dateString.contains(it.nameEs, ignoreCase = true)
        }
        mutableIntStateOf(found?.number ?: 3)
    }

    var selectedYear by remember(dateString) {
        val yearRegex = Regex("""\b(19\d\d|20\d\d)\b""")
        val match = yearRegex.find(dateString)
        mutableIntStateOf(match?.value?.toIntOrNull() ?: 1985)
    }

    var dayInput by remember(dateString) {
        val clean = dateString.replace(Regex("""\b(19\d\d|20\d\d)\b"""), "")
        val dayRegex = Regex("""\b([1-9]|[12]\d|3[01])\b""")
        val match = dayRegex.find(clean)
        mutableStateOf(match?.value ?: "15")
    }

    var isMonthDropdownExpanded by remember { mutableStateOf(false) }

    fun updateFormattedDate(m: Int, dStr: String, y: Int) {
        val maxDays = maxDaysForMonth(m, y)
        val validDay = (dStr.toIntOrNull() ?: 1).coerceIn(1, maxDays)
        val monthEn = MONTHS.firstOrNull { it.number == m }?.nameEn ?: "March"
        val formatted = "$monthEn $validDay, $y"
        onDateChanged(formatted)
    }

    val allYears = remember { (1920..2026).toList() }
    val cylinderListState = rememberLazyListState()
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = cylinderListState)
    val density = LocalDensity.current

    val itemWidth = 84.dp
    val itemSpacing = 8.dp
    val slotWidthPx = with(density) { (itemWidth + itemSpacing).toPx() }

    // Scroll to center selected year when changed programmatically
    LaunchedEffect(selectedYear) {
        val idx = allYears.indexOf(selectedYear)
        if (idx >= 0 && !cylinderListState.isScrollInProgress) {
            cylinderListState.animateScrollToItem(idx)
        }
    }

    // Centering & snapping commit when finger is lifted
    LaunchedEffect(cylinderListState.isScrollInProgress) {
        if (!cylinderListState.isScrollInProgress) {
            val offset = cylinderListState.firstVisibleItemScrollOffset
            val extraIndex = if (offset > (slotWidthPx / 2f)) 1 else 0
            val closestIndex = (cylinderListState.firstVisibleItemIndex + extraIndex).coerceIn(0, allYears.lastIndex)
            val snappedYear = allYears[closestIndex]
            if (snappedYear != selectedYear) {
                selectedYear = snappedYear
                updateFormattedDate(selectedMonth, dayInput, snappedYear)
            }
            cylinderListState.animateScrollToItem(closestIndex)
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("civics_date_picker_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Icon + Label + Current Selected Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    val currentMonthEn = MONTHS.firstOrNull { it.number == selectedMonth }?.nameEn ?: "March"
                    val dayNum = dayInput.toIntOrNull() ?: 15
                    Text(
                        text = "$currentMonthEn $dayNum, $selectedYear",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Row 1: Month Dropdown + Day Numeric Field (60dp touch-friendly height)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Month Selector (Dropdown)
                Box(modifier = Modifier.weight(1.5f)) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { isMonthDropdownExpanded = true }
                            .testTag("date_picker_month_dropdown"),
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val cur = MONTHS.firstOrNull { it.number == selectedMonth } ?: MONTHS[2]
                            Column {
                                Text(
                                    text = AppStrings.dateMonthLabel(language),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = cur.nameEn,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Select Month",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = isMonthDropdownExpanded,
                        onDismissRequest = { isMonthDropdownExpanded = false },
                        modifier = Modifier
                            .heightIn(max = 280.dp)
                            .testTag("month_dropdown_menu")
                    ) {
                        MONTHS.forEach { m ->
                            val isSelected = m.number == selectedMonth
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "${m.number}. ${m.nameEn} (${m.nameEs})",
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    selectedMonth = m.number
                                    isMonthDropdownExpanded = false
                                    updateFormattedDate(m.number, dayInput, selectedYear)
                                }
                            )
                        }
                    }
                }

                // Day Selector: Numeric Input
                OutlinedTextField(
                    value = dayInput,
                    onValueChange = { input ->
                        val digits = input.filter { it.isDigit() }.take(2)
                        dayInput = digits
                        val num = digits.toIntOrNull()
                        val maxDays = maxDaysForMonth(selectedMonth, selectedYear)
                        if (num != null && num in 1..maxDays) {
                            updateFormattedDate(selectedMonth, digits, selectedYear)
                        }
                    },
                    label = { Text(AppStrings.dateDayLabel(language), fontSize = 11.sp) },
                    placeholder = { Text("1-31") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .testTag("date_picker_day_input"),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Row 2: YEAR HORIZONTAL CYLINDER (Cilindro acostado táctil con auto-centrado)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant), RoundedCornerShape(14.dp))
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = AppStrings.dateYearLabel(language),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Text(
                        text = "↔ ${selectedYear}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // The Horizontal Cylinder Roller with exact viewport center padding
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f),
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    val halfWidth = maxWidth / 2
                    val centerPadding = (halfWidth - (itemWidth / 2)).coerceAtLeast(0.dp)

                    // Center cylinder highlight frame
                    Surface(
                        modifier = Modifier
                            .width(itemWidth)
                            .height(54.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        border = BorderStroke(2.5.dp, MaterialTheme.colorScheme.primary)
                    ) {}

                    // Horizontal Draggable Cylinder List with snapping behavior
                    LazyRow(
                        state = cylinderListState,
                        flingBehavior = snapFlingBehavior,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(68.dp)
                            .testTag("year_horizontal_cylinder"),
                        contentPadding = PaddingValues(horizontal = centerPadding),
                        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        itemsIndexed(allYears) { idx, yr ->
                            val isSelected = yr == selectedYear
                            Box(
                                modifier = Modifier
                                    .width(itemWidth)
                                    .height(52.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        selectedYear = yr
                                        updateFormattedDate(selectedMonth, dayInput, yr)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = yr.toString(),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal,
                                        fontSize = if (isSelected) 22.sp else 15.sp,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Touch controls: Left / Right arrows for fine tactile adjustments
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (selectedYear > 1920) {
                                    selectedYear -= 1
                                    updateFormattedDate(selectedMonth, dayInput, selectedYear)
                                }
                            },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChevronLeft,
                                contentDescription = "Año anterior",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = {
                                if (selectedYear < 2026) {
                                    selectedYear += 1
                                    updateFormattedDate(selectedMonth, dayInput, selectedYear)
                                }
                            },
                            modifier = Modifier.size(34.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Año siguiente",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
