package com.example.lendvest.utils

import java.math.BigDecimal
import java.util.*


class MathUtil {

    companion object {

        private val random: Random = Random()

        fun rand(from: Int, to: Int): Int {
            return random.nextInt(to - from) + from
        }

//    fun rand(from: Float, to: Float) : Float {
//        return random.nextFloat(to - from) + from
//    }

        fun rand(from: BigDecimal, to: BigDecimal): BigDecimal? {
            val randomBigDecimal: BigDecimal =
                from.add(BigDecimal(java.lang.Math.random()).multiply(to.subtract(from)))
            return randomBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)
        }

//    fun rand(from: Double, to: Double): Double{
//        val number:Double = 0.0449999
//        val number3digits:Double = java.lang.Math.random(number * 1000.0) / 1000.0
//        val number2digits:Double = random.nextInt(number3digits * 100.0) / 100.0
//        return random.nextInt(number2digits * 10.0) / 10.0
//    }
    }
}