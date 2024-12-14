package com.example.sqlite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
class Person {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var name = ""
    var age = 0

    constructor()

    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }
}