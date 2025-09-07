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
        } else if (item.title=="Entertainment") {
            R.drawable.ic_youtube
        }else if(item.title=="Freelance Project"){
            R.drawable.ic_upwork
        }else if (item.title=="Salary"){
            R.drawable.salary
        }else if (item.title=="Groceries"){
            R.drawable.grocery
        }else if (item.title=="Dining Out"){
            R.drawable.dining
        }else if (item.title=="Gym Membership"){
            R.drawable.gym
        }else if (item.title=="Stock Dividend"){
            R.drawable.stock
        }else {
            R.drawable.ic_upwork
        }
    }
}