package com.example.lendvest.models.data

import androidx.room.Embedded
import androidx.room.Relation

data class UserExpense(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "ownerId"
    )
    val expenses: List<Expense>
)
