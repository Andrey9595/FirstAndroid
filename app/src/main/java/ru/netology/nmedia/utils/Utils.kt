package ru.netology.nmedia.utils

class Utils {
    companion object {
        fun numPostfix(num: Int): String = when (num) {
            in 0..999 -> num.toString()
            in 1_000..9_999 -> if (num.toString()[1] == '0') "${num.toString()[0]}K" else "${num.toString()[0]},${num.toString()[1]}K"
            in 10_000..999_999 -> "${num.toString().dropLast(3)}K"
            else -> if (num.toString()[1] == '0') "${num.toString()[0]}M" else "${num.toString()[0]},${num.toString()[1]}M"
        }
    }
}