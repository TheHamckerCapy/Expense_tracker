package com.example.expense_tracker.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expense_tracker.R
import com.example.expense_tracker.Utils
import com.example.expense_tracker.data.model.ExpenseEntity
import com.example.expense_tracker.ui.theme.Zinc
import com.example.expense_tracker.widgets.ExpenseTextView

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel =
        HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (nameRow, lists, card, topBar, add) = createRefs()
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
                    .padding(
                        top = 64.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .constrainAs(nameRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Column(

                ) {
                    ExpenseTextView(
                        text = "Good Afternoon",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    ExpenseTextView(
                        text = "Jhon Doe",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Image(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

            }
            val state = viewModel.expenses.collectAsState(initial = emptyList())
            val balance = viewModel.getBalance(state.value)
            val income = viewModel.getTotalIncome(state.value)
            val expense = viewModel.getTotalExpense(state.value)
            CardItem(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                balance, income, expense
            )
            TransactionList(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(lists) {
                        top.linkTo(card.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints

                    },
                list = state.value

            )
            Image(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(add) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(bottom = 20.dp, end = 20.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Zinc)
                    .clickable {
                        navController.navigate("/add")
                    }
            )

        }
    }
}

@Composable
fun CardItem(modifier: Modifier = Modifier, balance: String, income: String, expense: String) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.error
                    )
                )
            )
            .padding(16.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                ExpenseTextView(
                    text = "Total Balance",
                    fontSize = 16.sp,
                    color = Color.White

                )
                ExpenseTextView(
                    text = balance,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White

                )

            }
            Image(
                painter = painterResource(R.drawable.dots_menu),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),

            ) {
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterStart),
                title = "Income",
                value = income,
                Image = R.drawable.ic_income
            )
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterEnd),
                title = "Expense",
                value = expense,
                Image = R.drawable.ic_expense
            )
        }

    }

}

@Composable
fun TransactionList(
    modifier: Modifier = Modifier,
    list: List<ExpenseEntity>,
    title: String = "Recent Transactions"
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                ExpenseTextView(
                    text = title,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                if (title == "Recent Transactions") {
                    ExpenseTextView(
                        text = "See All",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }


            }
        }
        items(list) { item ->
            val icon = Utils.getItemIcon(item)
            TransactionListItems(
                title = item.title,
                icon = icon,
                date = item.date,
                amount = item.amount.toString(),
                color = if (item.type == "Income") Color.Green else Color.Red,
            )


        }

    }
}

@Composable
fun TransactionListItems(title: String, icon: Int, date: Long, amount: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(

        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column(

            ) {
                ExpenseTextView(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                ExpenseTextView(
                    text = Utils.formatDateToHumanReadableForm(date),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

        }
        ExpenseTextView(
            text = amount,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterEnd),
            color = color
        )
    }
}

@Composable
fun CardRowItem(
    modifier: Modifier,
    title: String,
    value: String,
    Image: Int
) {
    Column(
        modifier = modifier
    ) {
        Row {
            Image(
                painter = painterResource(Image),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(8.dp))
            ExpenseTextView(
                text = title,
                fontSize = 16.sp,
                color = Color.White,

                )
        }
        ExpenseTextView(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,

            )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}