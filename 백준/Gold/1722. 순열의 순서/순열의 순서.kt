class P1722 {

    fun factorial(n: Int): Long {
        if (n == 1) return 1L
        return n * factorial(n - 1)
    }

    fun solve1(n: Int, k: Long): String {
        val sb = StringBuilder()
        val nums = MutableList(n) { it + 1 }
        var q = factorial(n)
        var _k = k - 1
        repeat(n) {
            q /= (n - it)

            val order = (_k / q).toInt()
            val num = nums.removeAt(order)
            sb.append(num).append(' ')

            _k -= order * q
        }
        return sb.toString()
    }

    fun solve2(n: Int, seq: List<Int>): String {
        var k = 1L
        val nums = MutableList(n) { it + 1 }
        var q = factorial(n)
        repeat(n) {
            q /= (n - it)

            val num = seq[it]
            val order = nums.indexOf(num)
            nums.removeAt(order)

            k += order * q
        }
        return k.toString()
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()
        val inp = readLine().split(" ")

        // 풀이
        val answer = if (inp[0] == "1") solve1(n, inp[1].toLong())
        else solve2(n, inp.slice(1..inp.lastIndex).map { it.toInt() })

        // 출력
        println(answer)
    }
}


fun main() = with(P1722()) {
    solve()
}