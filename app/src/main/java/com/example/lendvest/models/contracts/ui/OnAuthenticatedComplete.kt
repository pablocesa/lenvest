package com.example.lendvest.models.contracts.ui

import com.example.lendvest.models.data.Role

interface OnAuthenticatedComplete{
    fun setup(role: Role)
}
