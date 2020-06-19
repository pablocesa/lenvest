package com.example.lendvest.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lendvest.models.contracts.NotificationType
import com.example.lendvest.models.ui.ItemViewModel

@Entity
open class Notification(
    @PrimaryKey open val id: Long,
    var notificationType: NotificationType,
    var body: String = "",
    var iconResId: Int  = 0
): ItemViewModel()