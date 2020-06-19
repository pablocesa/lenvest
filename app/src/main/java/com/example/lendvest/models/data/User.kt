package com.example.lendvest.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lendvest.models.contracts.NotificationType
import com.example.lendvest.models.ui.ItemViewModel

@Entity(primaryKeys = ["id"])
class User(
     id: Long,
    var firstName: String = "",
    var lastName:  String = "",
    var email:     String = "",
    var avatar:    Int  = 0
): Notification(id, NotificationType.CONTRIBUTION)