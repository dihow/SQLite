package com.example.sqlite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {
    @Query("select * from persons")
    fun getPersons(): LiveData<List<Person>>

    @Insert
    fun insertPerson(person: Person)

    @Query("delete from persons where id = :id")
    fun deletePerson(id: Int)
}