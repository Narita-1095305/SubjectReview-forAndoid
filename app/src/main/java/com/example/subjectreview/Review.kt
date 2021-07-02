package com.example.subjectreview

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Review : RealmObject(){
    @PrimaryKey
    var id: Long = 0
    var date: Date = Date()
    var year: Date = Date()
    //var title: String = ""
    var teacher: String = ""
    var point: Int = 0
    var detail: String = ""
}