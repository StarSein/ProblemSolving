import kotlin.math.max


class P14863 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, k) = readLine().split(" ").map(String::toInt)
        val intervals = Array(n) { readLine().split(" ").map(String::toInt).toIntArray() }

        // 풀이
        val dp = Array(n + 1) { IntArray(k + 1) { -1 } }
        dp[0][0] = 0
        intervals.forEachIndexed { i, (wt, wa, bt, ba) ->
            repeat(k + 1) { j ->
                if (dp[i][j] != -1) {
                    if (j + wt <= k) dp[i+1][j+wt] = max(dp[i+1][j+wt], dp[i][j] + wa)
                    if (j + bt <= k) dp[i+1][j+bt] = max(dp[i+1][j+bt], dp[i][j] + ba)
                }
            }
        }
        val answer = dp[n].max()

        // 출력
        println(answer)
    }
}


fun main() = with(P14863()) {
    solve()
}