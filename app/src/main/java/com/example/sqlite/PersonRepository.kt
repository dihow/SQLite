package com.example.sqlite

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonRepository(private val personDao: PersonDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val personList: LiveData<List<Person>> = personDao.getPersons()

    fun insertPerson(person: Person) {
        coroutineScope.launch(Dispatchers.IO) {
            personDao.insertPerson(person)
        }
    }

    fun deletePerson(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            personDao.deletePerson(id)
        }
    }
}