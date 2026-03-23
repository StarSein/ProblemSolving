import kotlin.math.min

/**
 *
 * 상태(에너지 사용량, 방향)에 대해 제식 수행 횟수의 최솟값을 유지 및 전파하면 된다.
 * - DP
 * - 시간 복잡도 O(K)
 * - 공간 복잡도 O(K)
 *
 */


class P27114 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (a, b, c, k) = readLine().split(" ").map { it.toInt() }

        // 풀이
        val inf = 1_000_000_000
        val dp = Array(k + 1) { IntArray(4) { inf } }
        dp[0][0] = 0
        for (i in 0..k) {
            for (j in 0..3) {
                if (i - a >= 0) dp[i][j] = min(dp[i][j], dp[i-a][(j+1)%4] + 1)
                if (i - b >= 0) dp[i][j] = min(dp[i][j], dp[i-b][(j+3)%4] + 1)
                if (i - c >= 0) dp[i][j] = min(dp[i][j], dp[i-c][(j+2)%4] + 1)
            }
        }

        // 출력
        val answer = if (dp[k][0] == inf) -1 else dp[k][0]
        println(answer)
    }
}


fun main() = with(P27114()) {
    solve()
}