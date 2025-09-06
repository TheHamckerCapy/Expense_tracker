package com.example.expense_tracker.feature.add_expense

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expense_tracker.data.Dao.ExpenseDao
import com.example.expense_tracker.data.ExpenseDataBase
import com.example.expense_tracker.data.model.ExpenseEntity


class AddExpenseViewModel(val dao: ExpenseDao) : ViewModel(){

    suspend fun addExpense(expenseEntity: ExpenseEntity): Boolean{

        return try {
            dao.insertExpense(expenseEntity)
            return true
        }catch (ex: Throwable){
             false
        }

    }
}

class AddExpenseViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            val dao = ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return AddExpenseViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

