package com.example.androidstatehoistingcomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidstatehoistingcomposable.ui.theme.AndroidStateHoistingComposableTheme
import com.example.androidstatehoistingcomposable.viewmodels.ShoppingListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidStateHoistingComposableTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingListApp()
                }
            }
        }
    }
}

@Composable
//fun ShoppingListApp() {
    // state handling moved to ShoppingListViewModel.kt and stateFlow is used
    fun ShoppingListApp(viewModel: ShoppingListViewModel = viewModel()) {

    // state hoisted to ShoppingListApp function
    //var shoppingList by remember { mutableStateOf(listOf<String>()) }

        // CollectAsState is for observing the state of the Flow inside Composable functions.
        val shoppingList by viewModel.shoppingList.collectAsState()

    // lambda function to add item to the list
    //val addItem = { item: String ->
    //    shoppingList = shoppingList + item
    //}

    // lambda to remove an item from the list
    //val removeItem = { item: String ->
    //    shoppingList = shoppingList - item
    //}

   // ShoppingListScreen(shoppingList, addItem, removeItem)
    ShoppingListScreen(shoppingItems = shoppingList, onAddItem = viewModel::addItem, onRemoveItem = viewModel::removeItem)

}



@Composable
fun ShoppingListScreen(shoppingItems: List<String>, onAddItem: (String) -> Unit, onRemoveItem: (String) -> Unit) {
    Column {
        AddItemInput(onAddItem)
        Divider()
        ShoppingItemsList(shoppingItems, onRemoveItem)
    }
}

@Composable
fun AddItemInput(onAddItem: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Add item") }
        )
        Button(
            onClick = {
            onAddItem(text)
            text = ""
        }) {
            Text("Add")
        }
    }
}

@Composable
fun ShoppingItemsList(items: List<String>, onRemoveItem: (String) -> Unit) {
    LazyColumn {
        items(items) { item ->
            ShoppingItem(item, onRemoveItem)
        }
    }
}

@Composable
fun ShoppingItem(item: String, onRemoveItem: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = item, modifier = Modifier.weight(1f))
        IconButton(onClick = { onRemoveItem(item) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
