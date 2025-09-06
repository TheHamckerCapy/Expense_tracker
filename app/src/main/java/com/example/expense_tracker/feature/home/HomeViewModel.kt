package com.example.expense_tracker.feature.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expense_tracker.Utils
import com.example.expense_tracker.data.Dao.ExpenseDao
import com.example.expense_tracker.data.ExpenseDataBase
import com.example.expense_tracker.data.model.ExpenseEntity

class HomeViewModel(dao: ExpenseDao) : ViewModel() {
    val expenses = dao.getALlExpenses()

    fun getBalance(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach { it ->
            if (it.type == "Income") {
                total += it.amount
            } else {
                total -= it.amount
            }

        }
        return "$ ${Utils.formatToDecimals(total)}"

    }

    fun getTotalExpense(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach { it ->
            if (it.type == "Expense") {
                total += it.amount
            }

        }
        return "$ ${Utils.formatToDecimals(total)}"
    }

    fun getTotalIncome(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach { it ->
            if (it.type == "Income") {
                total += it.amount
            }

        }
        return "$ ${Utils.formatToDecimals(total)}"
    }

}

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao = ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}