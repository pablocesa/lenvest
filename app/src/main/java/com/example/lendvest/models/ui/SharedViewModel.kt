package com.example.lendvest.models.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class PendingLoanOption {
    ALL, PERSONAL
}

class SharedViewModel : ViewModel() {
    private val aDouble: MutableLiveData<Double> = MutableLiveData()
    private val pendingLoanOption: MutableLiveData<PendingLoanOption> = MutableLiveData()

    fun setPendingLoanOption(option: PendingLoanOption) {
        this.pendingLoanOption.value = option
    }

    fun getPendingLoanOption() : LiveData<PendingLoanOption> {
        return pendingLoanOption;
    }
}