package com.example.expense_tracker.feature.stats

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expense_tracker.Utils
import com.example.expense_tracker.data.Dao.ExpenseDao
import com.example.expense_tracker.data.ExpenseDataBase
import com.example.expense_tracker.data.model.ExpenseEntity
import com.example.expense_tracker.data.model.ExpenseSummary
import com.github.mikephil.charting.data.Entry

class StatsViewModel(dao: ExpenseDao) : ViewModel() {
    val entries = dao.getAllExpensesByDate()
    val topEntries = dao.getTopExpenses()
    val incomeEntries = dao.getAllIncomeByDate()
    val topIncome = dao.getTopIncome()




    fun getEntriesForChart(entries: List<ExpenseSummary>): List<Entry>{
        val list = mutableListOf<Entry>()
        for(entry in entries){
            list.add(Entry(entry.date.toFloat(), entry.totalSum.toFloat()))
        }
        return list
    }

}

class StatsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            val dao = ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return StatsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}