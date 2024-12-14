package com.example.sqlite

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sqlite.ui.theme.SQLiteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SQLiteTheme {
                val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    val viewModel: PersonViewModel = viewModel(
                        it,
                        "PersonViewModel",
                        PersonViewModelFactory(LocalContext.current.applicationContext as Application)
                    )
                    Program(viewModel)
                }
            }
        }
    }
}

class PersonViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonViewModel(application) as T
    }
}

@Composable
fun Program(viewModel: PersonViewModel = viewModel()) {
    val personList by viewModel.personList.observeAsState(listOf())
    Column {
        TextField(viewModel.name, modifier = Modifier.padding(12.dp),
            label = { Text("ФИО:") },
            onValueChange = { viewModel.changeName(it) })
        TextField(viewModel.age.toString(), modifier = Modifier.padding(12.dp),
            label = { Text("Возраст:") },
            onValueChange = { viewModel.changeAge(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(onClick = { viewModel.insertPerson() }, modifier = Modifier.padding(12.dp)) {
            Text("Добавить", fontSize = 22.sp)
        }
        TextField(viewModel.id.toString(), modifier = Modifier.padding(12.dp),
            label = { Text("Id: ") },
            onValueChange = { viewModel.changeId(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(onClick = { viewModel.deletePerson(viewModel.id) }, Modifier.padding(12.dp)) {
            Text("Удалить", fontSize = 22.sp)
        }
        PersonTable(personList)
    }
}

@Composable
fun PersonTable(persons: List<Person>) {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            PersonTitleRow()
        }
        items(persons) {
            PersonRow(it)
        }
    }
}

@Composable
fun PersonRow(person: Person) {
    Row(Modifier
        .background(Color.Gray)
        .fillMaxWidth()
        .padding(5.dp)) {
        Text(person.id.toString(), modifier = Modifier.weight(.2f), fontSize = 22.sp)
        Text(person.name, modifier = Modifier.weight(.8f), fontSize = 22.sp)
        Text(person.age.toString(), modifier = Modifier.weight(.4f), fontSize = 22.sp)
    }
}

@Composable
fun PersonTitleRow() {
    Row(Modifier
        .background(Color.Gray)
        .fillMaxWidth()
        .padding(5.dp)) {
        Text("Id", color = Color.White, modifier = Modifier.weight(.2f), fontSize = 22.sp)
        Text("ФИО", color = Color.White, modifier = Modifier.weight(.8f), fontSize = 22.sp)
        Text("Возраст", color = Color.White, modifier = Modifier.weight(.4f), fontSize = 22.sp)
    }
}