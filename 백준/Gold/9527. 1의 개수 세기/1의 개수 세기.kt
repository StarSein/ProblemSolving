/**
 * 0000
 * 0001
 * 0010
 * 0011
 * 0100
 * 0101
 * 0110
 * 0111
 * 1000
 *
 * g(x) = f(0) + f(1) + f(2) + ... + f(x) 라고 하자
 * 구하는 값 = g(b) - g(a - 1)
 *
 * g(7) = 4 + 2 * g(3)
 * g(3) = 2 + 2 * g(1)
 * g(5) = 2 + g(3) + g(1)
 * g(6) = 3 + g(3) + g(2)
 * g(8) = 1 + g(7) + g(0)
 * g(9) = 2 + g(7) + g(1)
 *
 * 시간 복잡도 O(logN)
 *
 */


class P9527 {

    val cache = hashMapOf<Long, Long>()

    fun g(x: Long): Long {
        if (x <= 1) return x
        if (cache.containsKey(x)) return cache.getOrDefault(x, 0)
        val y = x.takeHighestOneBit() - 1
        val diff = x - y
        val ret = diff + g(y) + g(diff - 1)
        cache[x] = ret
        return ret
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (a, b) = readLine().split(" ").map { it.toLong() }

        // 풀이
        val answer = g(b) - g(a - 1)

        // 출력
        println(answer)
    }
}


fun main() = with(P9527()) {
    solve()
}