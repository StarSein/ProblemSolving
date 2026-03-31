import kotlin.math.max

class P31434 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, k) = readLine().split(" ").map { it.toInt() }
        val items = Array(n) { readLine().split(" ").map { it.toInt() } }
            .map { it[0] to it[1] }

        // 풀이
        val maxS = 3000
        val dp = IntArray(maxS + 1) { -1 }
        // dp[i]: s == i 일 때 당근의 최대 보유량 (-1인 경우 아직 불가능한 경우)
        dp[1] = 0

        repeat(k) {
            for (i in maxS downTo 1) {
                val carrot = dp[i]
                if (carrot == -1) continue

                // 행동 2
                for ((a, b) in items) {
                    if (carrot < a || i + b > maxS) continue
                    dp[i+b] = max(dp[i+b], carrot - a)
                }

                // 행동 1
                dp[i] += i
            }
        }

        // 출력
        val answer = dp.max()
        println(answer)
    }

}

fun main() = with(P31434()) {
    solve()
}