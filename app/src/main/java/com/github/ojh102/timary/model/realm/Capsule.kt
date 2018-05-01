package com.github.ojh102.timary.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Capsule : RealmObject() {
    @PrimaryKey
    var id: Long = 0L

    var content: String = ""
    var targetDate: Long = 0L
    var writtenDate: Long = 0L

    fun dDay(): Float {
        val diff = targetDate - System.currentTimeMillis()
        val diffDay = (diff.toFloat() / (24 * 60 * 60 * 1000))

        return diffDay
    }

    fun isOpened(): Boolean {
        return dDay() <= 0f
    }

}