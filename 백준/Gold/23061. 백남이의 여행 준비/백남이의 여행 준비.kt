import kotlin.math.max

class P23061 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, m) = readLine().split(" ").map { it.toInt() }
        val items = Array(n) { readLine().split(" ").map { it.toInt() } }
            .map { it[0] to it[1] } // (w, v)
        val caps = Array(m) { readLine().toInt() }

        // 풀이
        val capsLimit = 1_000_000
        val dp = Array(capsLimit + 1) { -1 }
        dp[0] = 0
        items.forEach { (w, v) ->
            for (i in capsLimit - w downTo 0) {
                if (dp[i] == -1) continue
                dp[i + w] = max(dp[i + w], dp[i] + v)
            }
        }

        for (i in 1..capsLimit) {
            dp[i] = max(dp[i], dp[i - 1])
        }

        var answer = -1
        var score = -1.0
        caps.forEachIndexed { i, cap ->
            val curScore = dp[cap].toDouble() / cap
            if (curScore > score) {
                answer = i + 1
                score = curScore
            }
        }

        // 출력
        println(answer)
    }
}

fun main() = with(P23061()) {
    solve()
}