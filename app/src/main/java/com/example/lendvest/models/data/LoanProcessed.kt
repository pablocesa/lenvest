package com.example.lendvest.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lendvest.models.ui.ItemViewModel
import java.math.BigDecimal
import java.time.LocalDateTime


@Entity
class LoanProcessed (
    @PrimaryKey override val id: Long,
    ownerId:     Long,
    name:         String = "",
    loanAmount:       BigDecimal? = BigDecimal.ZERO,
    override var interestRate: BigDecimal? = BigDecimal.ZERO,
    override var loanDate: LocalDateTime = LocalDateTime.now(),
    icon:         Int = 0,
    override var numberOfYears: Int = 0
): Loan(id, ownerId, name, loanAmount, interestRate, loanDate, icon, numberOfYears) {

//    var amountPledge    = BigDecimal(0)
//    var percentagePledge: Double = 0.0
//    var isComplete: Boolean = false
//
//    init {
//        percentagePledge =  (amountPledge.toDouble() / loanAmount!!.toDouble()) * 100
//    }

}