import kotlin.math.max
import kotlin.math.min

class P4970 {

    var inp = ""
    val pqr = intArrayOf(-1, -1, -1)
    var idx = 0
    val operators = charArrayOf('*', '+')
    val chars = charArrayOf('P', 'Q', 'R')

    fun calc(): Int {
        var i = 0
        val terms = intArrayOf(-1, -1)
        val nots = booleanArrayOf(false, false)
        var operator = '.'
        while (idx < inp.length) {
            when (val c = inp[idx++]) {
                '(' -> terms[i] = calc()
                ')' -> {
                    (0..1).forEach {
                        if (nots[it]) {
                            terms[it] = 2 - terms[it]
                        }
                    }
                    return when (operator) {
                        '*' -> min(terms[0], terms[1])
                        '+' -> max(terms[0], terms[1])
                        else -> terms[0]
                    }
                }
                '-' -> nots[i] = !nots[i]
                in operators -> { operator = c; i++ }
                in chars -> { terms[i] = pqr[c - 'P'] }
                else -> { terms[i] = c - '0' }
            }
        }
        if (nots[0]) {
            terms[0] = 2 - terms[0]
        }
        return terms[0]
    }

    fun power(i: Int): Int {
        if (i == pqr.size) {
            idx = 0
            return if (calc() == 2) 1 else 0
        }
        var res = 0
        for (x in 0..2) {
            pqr[i] = x
            res += power(i + 1)
        }
        return res
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        val sb = StringBuilder()

        var answer: Int
        while (true) {
            inp = readLine()?.trim() ?: break
            if (inp == ".") break
            answer = power(0)
            sb.append(answer).append('\n')
        }
        print(sb)
    }

}


fun main() = P4970().solve()