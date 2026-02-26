/**
 *
 * # 접근
 * - DP
 * - 시간 O(N^3) (N <= 100) - 이 경우 재귀 호출이 유의미한 오버헤드가 되므로 Bottom-up으로 점화식 전개
 * - 공간 O(N^3)
 * 1. dp[i][j][k]: i초에 기웅이가 j, 민수가 k에 위치하는 경우의 수 (j < k)
 * 2. dp[i][j][k] = dp[i-1][j-1][k-1] + dp[i-1][j-1][k] + dp[i-1][j][k-1] + dp[i-1][j][k]
 *    (단 j != k 인 경우에만 해당. j == k 인 경우 dp[i][j][k] = 0)
 * 3. (sum(dp[N]) * 2) % 10_007 을 출력한다 (민수가 j, 기웅이가 k인 경우까지 고려해야 하므로)
 *
 */

class P14945 {

    val mod = 10_007

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()

        // 풀이
        val dp = Array(n+1) { Array(n+1) { IntArray(n+1) { 0 } } }
        dp[2][1][2] = 1
        for (i in 3..n) {
            for (j in 1 until i) {
                for (k in j+1..i) {
                    dp[i][j][k] = (dp[i-1][j-1][k-1] + dp[i-1][j][k-1] + dp[i-1][j-1][k] + dp[i-1][j][k]) % mod
                }
            }
        }
        val answer = (2 * dp[n].sumOf { arr -> arr.sum() }) % mod

        // 출력
        println(answer)
    }
}

fun main() = with(P14945()) {
    solve()
}