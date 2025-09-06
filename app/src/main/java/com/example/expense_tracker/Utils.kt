package com.example.expense_tracker

import android.annotation.SuppressLint
import androidx.compose.ui.platform.LocalContext
import com.example.expense_tracker.data.model.ExpenseEntity
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun formatDateToHumanReadableForm(date: Long): String{
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormatter.format(date)

    }
    @SuppressLint("DefaultLocale")
    fun formatToDecimals(d:Double): String{
        return String.format("%.2f",d)
    }
    fun formatDateForChart(date: Long): String{
        val dateFormatter = SimpleDateFormat("dd-MMM", Locale.getDefault())
        return dateFormatter.format(date)

    }
    fun getItemIcon(item: ExpenseEntity): Int{
        return if (item.title == "Paypal") {
            R.drawable.ic_paypal
        } else if (item.title == "Netflix") {
            R.drawable.ic_netflix
        } else if (item.title == "Starbucks") {
            R.drawable.ic_starbucks
        } else {
            R.drawable.ic_upwork
        }
    }
}