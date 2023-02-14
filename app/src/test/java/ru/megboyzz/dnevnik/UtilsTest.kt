package ru.megboyzz.dnevnik

import org.junit.Test

import org.junit.Assert.*
import ru.megboyzz.dnevnik.screens.ui.Month
import java.time.DayOfWeek

class UtilsTest {

    @Test
    fun testForDaysFunc(){
        val days = getMonthDaysBy(2023, Month.January, DayOfWeek.MONDAY)
        assertEquals(
            listOf(2, 9, 16, 23, 30),
            days
        )
    }
}