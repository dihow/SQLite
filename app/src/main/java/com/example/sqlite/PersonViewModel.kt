package com.example.sqlite

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PersonViewModel(application: Application) : ViewModel() {
    val personList: LiveData<List<Person>>
    private val repository: PersonRepository
    var id by mutableIntStateOf(0)
    var name by mutableStateOf("")
    var age by mutableIntStateOf(0)

    init {
        val personDb = PersonRoomDatabase.getInstance(application)
        val personDao = personDb.personDao()
        repository = PersonRepository(personDao)
        personList = repository.personList
    }

    fun changeId(value: String) {
        id = value.toIntOrNull() ?: id
    }

    fun changeName(value: String) {
        name = value
    }

    fun changeAge(value: String) {
        age = value.toIntOrNull() ?: age
    }

    fun insertPerson() {
        repository.insertPerson(Person(name, age))
    }

    fun deletePerson(id: Int) {
        repository.deletePerson(id)
    }
}