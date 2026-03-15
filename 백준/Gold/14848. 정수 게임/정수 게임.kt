/**
 *
 * f(X): 1부터 N까지의 정수 중 X의 배수의 개수
 * N <= 10^9 이므로 일일이 정수의 삭제를 관리할 수는 없다.
 *
 * A = {2, 3, 5} 라고 할 때
 * 정답은 N - f(2) - f(3) - f(5) + f(lcm(2,3)) + f(lcm(3,5)) + f(lcm(5,2)) - f(lcm(2,3,5))
 *
 * # 접근
 * - 포함 배제의 원리
 * - 시간 복잡도 O(2^K)
 * - 공간 복잡도 O(K)
 *
 */


class P14848 {

    var n = 0L
    var k = 0
    var arr = longArrayOf()

    fun gcd(a: Long, b: Long): Long {
        if (b == 0L) return a
        return gcd(b, a % b)
    }

    fun lcm(a: Long, b: Long): Long {
        return a / gcd(a, b) * b
    }

    fun recur(s: Int, x: Long): Long {
        if (x > n) return 0L
        var ret = n / x
        for (i in s..<k) {
            ret -= recur(i + 1, lcm(x, arr[i]))
        }
        return ret
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        readLine().split(" ").let {
            n = it[0].toLong()
            k = it[1].toInt()
        }
        arr = readLine().split(" ").map(String::toLong).toLongArray()

        // 풀이
        val answer = recur(0, 1L)

        // 출력
        println(answer)
    }
}


fun main() = with(P14848()) {
    solve()
}