package com.example.expense_tracker.data

import android.content.Context
import androidx.room.CoroutinesRoom
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.expense_tracker.data.Dao.ExpenseDao
import com.example.expense_tracker.data.model.ExpenseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [ExpenseEntity::class], version = 1)
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
                        dao.insertExpense(ExpenseEntity( 1,"Salary",5000.450,     System.currentTimeMillis(),"Salary","Income",  ))
                        dao.insertExpense(ExpenseEntity( 2,"Paypal",1000.0,     System.currentTimeMillis(),"Paypal","Expense",))
                        dao.insertExpense(ExpenseEntity( 3,"Netflix",  500.20,    System.currentTimeMillis(),"Netflix","Expense",))
                        dao.insertExpense(ExpenseEntity( 4,"StarBucks",  34000.0,    System.currentTimeMillis(),"StarBucks","Expense",))
                    }
                }
            }).build()
        }
    }

}
