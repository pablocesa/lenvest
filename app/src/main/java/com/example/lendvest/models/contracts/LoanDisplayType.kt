package com.example.lendvest.models.contracts

enum class LoanDisplayType {
    OFFERS,
    LOAN_REQUEST,
    MY_DEBTORS,
    DEBTS,
}

class LoanDisplay (val displayType: LoanDisplayType, val name: String)