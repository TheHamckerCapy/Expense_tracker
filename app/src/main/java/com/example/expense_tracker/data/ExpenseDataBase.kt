package com.example.expense_tracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.expense_tracker.data.Dao.ExpenseDao
import com.example.expense_tracker.data.model.ExpenseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [ExpenseEntity::class], version = 2)
abstract class ExpenseDataBase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "expense_db"

        @JvmStatic
        fun getDatabase(context: Context): ExpenseDataBase {
            return Room.databaseBuilder(
                context,
                ExpenseDataBase::class.java,
                DATABASE_NAME
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    InitBasicData(context)
                }

                fun InitBasicData(context: Context) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = getDatabase(context).expenseDao()
                        val DAY = 86_400_000L
                        val now = System.currentTimeMillis()
                        dao.insertExpense(
                            ExpenseEntity(1, "Salary", 65000.0, now - DAY * 28, "Income")
                        )
                        dao.insertExpense(
                            ExpenseEntity(2, "Freelance Project", 12000.0, now - DAY * 21, "Income")
                        )
                        dao.insertExpense(
                            ExpenseEntity(3, "Groceries", 3500.75, now - DAY * 18, "Expense")
                        )
                        dao.insertExpense(
                            ExpenseEntity(4, "Netflix", 499.0, now - DAY * 15, "Expense")
                        )
                        dao.insertExpense(
                            ExpenseEntity(5, "Electricity Bill", 2200.0, now - DAY * 12, "Expense")
                        )
                        dao.insertExpense(
                            ExpenseEntity(6, "Dining Out", 1800.50, now - DAY * 7, "Expense")
                        )
                        dao.insertExpense(
                            ExpenseEntity(7, "Gym Membership", 1500.0, now - DAY * 3, "Expense")
                        )
                        dao.insertExpense(
                            ExpenseEntity(8, "Stock Dividend", 2500.0, now, "Income")
                        )
                    }
                }
            }).build()
        }
    }

}
