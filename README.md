# Expense Tracker Android

Welcome to the **Expense Tracker Android** app!  
This project is designed to help users keep track of their daily expenses with ease.  
The app allows users to **add, view, and analyze expenses** using intuitive stats and charts.  
It is developed using modern Android development practices, including **Jetpack Compose**, **Room Database**,**Material 3 Ui**, and the **MVVM architecture**.

---

## ✨ Features
- **Add Expense**: Easily add your daily expenses with a few taps.  
- **Track Expenses**: View a list of all your expenses, organized by date and category.  
- **Analyze with Stats**: Get insights into your debit and credit  habits with detailed stats and charts.  

---

## 🛠️ Technologies Used
- **Jetpack Compose**: A modern toolkit for building native Android UI.  
- **Room Database**: A robust database layer on top of SQLite for managing local data.  
- **MVVM Architecture**: Model-View-ViewModel architecture for separating UI, business logic, and data handling.  

## Architecture
<img width="1071" height="509" alt="image" src="https://github.com/user-attachments/assets/354baefb-7324-4ea4-bccd-f5b84058ae73" />


````markdown
 🚀 Getting Started

 ✅ Prerequisites
- Android Studio -Bumblebee or later  
- Java 11 or later  
- Android SDK 21 or later  

 📥 Installation
1. Clone the repository:
   git clone https://github.com/yourusername/ExpenseTrackerAndroid.git
````
## 📦 Dependencies

Add the following to your **build.gradle (Module: app)** file:  

```kotlin
implementation("androidx.constraintlayout:constraintlayout:2.2.1")
implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")

val room_version = "2.7.2"
implementation("androidx.room:room-runtime:$room_version")
ksp("androidx.room:room-compiler:$room_version")
annotationProcessor("androidx.room:room-compiler:$room_version")
implementation("androidx.room:room-ktx:$room_version")

val nav_version = "2.9.3"
implementation("androidx.navigation:navigation-compose:$nav_version")

implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
```
---

### 🔄 Version Updates

Make sure to always check for the latest versions:

* [Room Release Notes](https://developer.android.com/jetpack/androidx/releases/room)
* [ConstraintLayout Release Notes](https://developer.android.com/jetpack/androidx/releases/constraintlayout)
* MpAndroidChart -https://github.com/PhilJay/MPAndroidChart
2. Open the project in **Android Studio**.
3. Sync the project with **Gradle files**.
4. Run the app on an **emulator** or **physical device**.

---

### 📱 Usage

#### ➕ Adding an Expense

1. Tap on the **"Add Expense"** button.
2. Enter the amount, select a category, and add notes (optional).
3. Save the expense to track it.

#### 📋 Viewing Expenses

* Navigate to the **"Expense List"** screen to view all recorded expenses.
* Tap on any expense to **edit or delete** it.

#### 📊 Tracking with Stats

* Go to the **"Stats"** section to view **charts and summaries** of your spending habits over time.

### ScreenShots and clip
<img src="https://github.com/user-attachments/assets/f7e78653-d5ef-40f9-86a9-68b42e904a89" width="300" />
<img src="https://github.com/user-attachments/assets/037f8003-dc80-4faf-80ca-e71a990a31f6" width="300" />
<img src="https://github.com/user-attachments/assets/90932f32-6d9e-4fa5-9962-f6f7ed9f47d9" width="300" />
<img src="https://github.com/user-attachments/assets/404b03bf-6f82-4c0f-a71d-0b3d481899ea" width="300" />




https://github.com/user-attachments/assets/46c927a7-0090-4290-9845-1a79c03120a9





---

*When you build and launch the app you may see already filled in data , this is because of the following snippet which was for development and testing purposes only . If you want to remove those data simply remove this code snippet from the expenseDatabase file and make you unistall and reinstall the app for the changes to take effect.*
```
.addCallback(object : Callback() {
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
            })
```



