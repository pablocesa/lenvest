package com.example.lendvest.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lendvest.models.contracts.NotificationType
import java.math.BigDecimal
import java.time.LocalDateTime


@Entity
open class Loan (
    @PrimaryKey override val id: Long,
    ownerId:     Long,
    name:         String = "",
    loanAmount:       BigDecimal? = BigDecimal.ZERO,
    open var interestRate: BigDecimal? = BigDecimal.ZERO,
    open var loanDate: LocalDateTime = LocalDateTime.now(),
    icon:         Int = 0,
    open var numberOfYears: Int = 0
): Expense(id, ownerId, NotificationType.LOAN, name, loanAmount, loanDate, icon) {

//    var percentagePledge: Double = 0.0
//    init {
//        percentagePledge =  (count.toDouble() / totalCount) * 100
//    }

    var amountPledge    = BigDecimal(0)
    var percentagePledge: Int = 0
    var isComplete: Boolean = false

    init {
        percentagePledge =  (amountPledge.toInt() / loanAmount!!.toInt()) * 100
    }

    fun getMonthlyPayment(): Double { // Convert interest rate into a decimal
// eg. 6.5% = 0.065
        var interestRateDouble = interestRate?.toDouble()
        var loanAmountDouble   = amount?.toDouble()

        interestRateDouble = interestRateDouble?.div(100.0)
        // Monthly interest rate
// is the yearly rate divided by 12
        val monthlyRate = interestRateDouble?.div(12.0)
        // The length of the term in months
// is the number of years times 12
        val termInMonths = numberOfYears * 12
        // Calculate the monthly payment
// Typically this formula is provided so
// we won't go into the details
// The Math.pow() method is used calculate values raised to a power
        if (loanAmountDouble != null) {
            return loanAmountDouble * monthlyRate!! /
                    (1 - Math.pow(1 + monthlyRate, -termInMonths.toDouble()))
        }
        return 0.0
    }

    /** Find total payment  */
    fun getTotalPayment(): Double {
        return getMonthlyPayment() * numberOfYears * 12
    }
}