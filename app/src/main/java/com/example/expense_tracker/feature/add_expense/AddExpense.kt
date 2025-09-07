package com.example.expense_tracker.feature.add_expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expense_tracker.R
import com.example.expense_tracker.Utils
import com.example.expense_tracker.data.model.ExpenseEntity
import com.example.expense_tracker.ui.theme.InterFontFamily
import com.example.expense_tracker.widgets.ExpenseTextView
import kotlinx.coroutines.launch

@Composable
fun AddExpense(navController: NavController) {
    val viewModel =
        AddExpenseViewModelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)
    val CoroutineScope = rememberCoroutineScope()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (nameRow, lists, card, topBar) = createRefs()
            Image(
                painter = painterResource(R.drawable.ic_topbar),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(nameRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                ExpenseTextView(
                    text = "Add Expenses",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
                Image(
                    painter = painterResource(R.drawable.dots_menu),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            DataForm(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    },
                onAddExpenseClick = {
                    CoroutineScope.launch {
                        if (viewModel.addExpense(it)) {
                            navController.popBackStack()
                        }
                    }

                }
            )
        }
    }
}

@Composable
fun DataForm(modifier: Modifier = Modifier, onAddExpenseClick: (model: ExpenseEntity) -> Unit) {

    var name by remember { mutableStateOf("") }
    var amt by remember { mutableStateOf("") }
    var date by remember { mutableLongStateOf(0L) }
    var dateDialogVisibility by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var amountError by remember { mutableStateOf<String?>(null) }
    var incomeError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())

    ) {
        ExpenseTextView(text = "Name", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.size(4.dp))
        ExpenseDropDown(
            listOf(
                "Paypal",
                "Salary",
                "Freelance",
                "Investments",
                "Bonus",
                "Rental Income",
                "Grocery",
                "Netflix",
                "Rent",
                "Paypal",
                "Starbucks",
                "Shopping",
                "Transport",
                "Utilities",
                "Dining Out",
                "Entertainment",
                "Healthcare",
                "Insurance",
                "Subscriptions",
                "Education",
                "Debt Payments",
                "Gifts & Donations",
                "Travel",
                "Other Expenses"

            ),
            onItemSelected = { name = it }
        )

        Spacer(modifier = Modifier.size(8.dp))
        ExpenseTextView(text = "Amount", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(
            value = amt,
            onValueChange = { amt = it ;amountError=null},
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(

                focusedBorderColor = Color.Black, focusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Black, unfocusedTextColor = Color.Black,
            ),
            isError = amountError!=null
        )
        amountError?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)}
        Spacer(modifier = Modifier.size(8.dp))
        // datePicker
        ExpenseTextView(text = "Date", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(
            value = if (date == 0L) "" else Utils.formatDateToHumanReadableForm(date),
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dateDialogVisibility = true },
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Black, disabledTextColor = Color.Black,
                disabledPlaceholderColor = Color.Black,
            ),
            placeholder = { ExpenseTextView(text = "Select date") }
        )
        Spacer(modifier = Modifier.size(8.dp))

        // type
        ExpenseTextView(text = "Type", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.size(4.dp))
        ExpenseDropDown(listitems = listOf("Income", "Expense"), onItemSelected = { type = it })

        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = {
                var isValid = true
                val amountDouble = amt.toDoubleOrNull()
                if(amt.isBlank()||  amountDouble==null|| amountDouble<=0){
                    amountError="Please enter a valid amount "
                    isValid=false
                }else{
                    amountError=null
                }
                if(isValid){
                    val model = ExpenseEntity(
                        id = null,
                        title = name,
                        amount = amt.toDoubleOrNull() ?: 0.0,
                        date = date,
                        type = type,

                        )
                    onAddExpenseClick(model)

                }


            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            ExpenseTextView(
                text = "Save"
            )
        }
        if (dateDialogVisibility) {
            ExpenseDatePickerDialog(
                onDateSelected = {
                    date = it
                    dateDialogVisibility = false
                },
                onDismiss = { dateDialogVisibility = false })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePickerDialog(
    onDateSelected: (date: Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                ExpenseTextView(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                ExpenseTextView(text = "Cancel")
            }
        }

    ) {
        DatePicker(state = datePickerState)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDropDown(listitems: List<String>, onItemSelected: (item: String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Please Select") }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            textStyle = TextStyle(fontFamily = InterFontFamily, color = Color.Black),
            readOnly = true,

            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                disabledBorderColor = Color.Black, disabledTextColor = Color.Black,
                disabledPlaceholderColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,

                )

        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = {}) {
            listitems.forEach {
                DropdownMenuItem(
                    text = { ExpenseTextView(text = it) },
                    onClick = {
                        selectedItem = it
                        onItemSelected(it)
                        expanded = false

                    }
                )
            }

        }
    }
}

@Preview
@Composable
private fun AddExpensePreview() {
    AddExpense(rememberNavController())
}