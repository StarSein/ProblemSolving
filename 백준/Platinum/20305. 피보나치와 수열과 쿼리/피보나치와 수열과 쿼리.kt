/**
 *  1 2 3 4 5 6 7  8  9  10
 *  1 1 2 3 5 8 13 21 34 55
 *
 *  0 1 2 3 4 5 6 7  8  9  10 11 12 13 14 15 16
 *  0 0 0 0 0 0 0 0  0  0  0  0  0  0  0  0  0
 *    1                      -89 -55
 *  0 1 1 2 3 5 8 13 21 34 55 0  0  0  0  0  0
 *    1         -8-5
 *            1   -2 -1
 *    2       1 -8-7 -1      -89 -55
 *  0 2 2 4 6 11 9.13.21 34 55 0 0  0  0  0  0
 *
 *  update(l, r)
 *  1. A[l] += 1
 *  2. A[r+1] -= fibo(r-l+2)
 *  3. A[r+2] -= fibo(r-l+1)
 *
 *  모든 update 처리가 끝난 뒤 배열 왼쪽부터 스위핑
 *  A[i+2] += A[i+1] + A[i]
 *
 */


class P20305 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()
        val q = readLine().toInt()

        // 쿼리 입력 처리
        val mod = 1_000_000_007L
        val arr = LongArray(n + 3) { 0 }
        val fib = LongArray(n + 3) { 0 }
        fib[1] = 1
        for (i in 2..fib.lastIndex) {
            fib[i] = (fib[i-1] + fib[i-2]) % mod
        }
        repeat(q) {
            val (l, r) = readLine().split(" ").map { it.toInt() }
            arr[l] = (arr[l] + 1) % mod
            arr[r+1] = (arr[r+1] - fib[r-l+2] + mod) % mod
            arr[r+2] = (arr[r+2] - fib[r-l+1] + mod) % mod
        }

        // 후처리
        for (i in 2..n) {
            arr[i] = (arr[i] + arr[i-1] + arr[i-2] + mod) % mod
        }

        // 출력
        println(arr.slice(1..n).joinToString(" "))
    }
}


fun main() = with(P20305()) {
    solve()
}