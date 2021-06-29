package com.example.subjectreview

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Subject : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var title: String = ""

}