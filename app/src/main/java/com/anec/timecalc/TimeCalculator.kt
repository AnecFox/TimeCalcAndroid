package com.anec.timecalc

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class TimeCalculator {

    fun getTimeInterval(firstTime: String, secondTime: String): IntArray {
        val firstTimeList: List<String> = firstTime.split(":")
        val firstTimeHours: Int = firstTimeList[0].toInt()
        val firstTimeMinutes: Int = firstTimeList[1].toInt()

        val firstDateTime: LocalDateTime = LocalDateTime.of(
            LocalDateTime.now().year, LocalDateTime.now().month,
            LocalDateTime.now().dayOfMonth, firstTimeHours, firstTimeMinutes
        )

        val secondTimeList: List<String> = secondTime.split(":")
        val secondTimeHours: Int = secondTimeList[0].toInt()
        val secondTimeMinutes: Int = secondTimeList[1].toInt()

        val secondDateTime = LocalDateTime.of(
            LocalDateTime.now().year,
            LocalDateTime.now().month,
            if (secondTimeHours < firstTimeHours ||
                (secondTimeMinutes < firstTimeMinutes && secondTimeHours == firstTimeHours)
            )
                LocalDateTime.now().dayOfMonth + 1 else LocalDateTime.now().dayOfMonth,
            secondTimeHours,
            secondTimeMinutes
        )

        var result = IntArray(2)
        result[1] = firstDateTime.until(secondDateTime, ChronoUnit.MINUTES).toInt()
        result = correctTime(result[0], result[1])
        return result
    }

    private fun correctTime(hours: Int, minutes: Int): IntArray {
        var newHours: Int = hours
        var newMinutes: Int = minutes
        while (newMinutes >= 60) {
            newHours++
            newMinutes -= 60
        }

        val result = IntArray(2)
        result[0] = newHours
        result[1] = newMinutes
        return result
    }

    // parameter words usage example: TimeCalculator.getHourWords() or TimeCalculator.getMinuteWords()
    fun generateWord(number: Int, words: Array<String>): String {
        val numberSymbols: CharArray = number.toString().toCharArray()
        val lastNumberSymbol: Int = numberSymbols[numberSymbols.size - 1].toString().toInt()

        return if (number.toString().endsWith("1") && number != 11) {
            words[0]
        } else if (lastNumberSymbol in 2..4 && number !in 12..14) {
            words[1]
        } else {
            words[2]
        }
    }

    fun getHourWords(): Array<String> {
        val words: Array<String> = Array(3) { "it = $it" }
        words[0] = MainActivity.applicationContext().resources.getString(R.string.hour_word_1)
        words[1] = MainActivity.applicationContext().resources.getString(R.string.hour_word_2)
        words[2] = MainActivity.applicationContext().resources.getString(R.string.hour_word_3)

        return words
    }

    fun getMinuteWords(): Array<String> {
        val words: Array<String> = Array(3) { "it = $it" }
        words[0] = MainActivity.applicationContext().resources.getString(R.string.minute_word_1)
        words[1] = MainActivity.applicationContext().resources.getString(R.string.minute_word_2)
        words[2] = MainActivity.applicationContext().resources.getString(R.string.minute_word_3)

        return words
    }

    companion object {
        fun addZeroToMinutesIfMinutesLessThan10(minutes: Int): String {
            return if (minutes < 10) {
                "0$minutes"
            } else {
                minutes.toString()
            }
        }
    }
}
