package com.example.lendvest.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lendvest.models.contracts.NotificationType
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
open class Expense(
    @PrimaryKey override val id: Long = 0,
    val ownerId:     Long,
    var expenseType: NotificationType,
    var type:        String = "",
    var amount:      BigDecimal? = BigDecimal.ZERO,
    var expenseDate: LocalDateTime = LocalDateTime.now(),
    var icon:        Int = 0,
    var text:        String = "",
    var statusColor: Int = 0
): Notification(id, expenseType)