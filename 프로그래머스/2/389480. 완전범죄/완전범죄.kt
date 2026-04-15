import kotlin.math.min


class Solution {
    
    val inf = 1_000
    val dp = Array(40) { Array(121) { IntArray(121) { -1 } } }
    
    fun recur(a: Int, b: Int, idx: Int, info: Array<IntArray>, n: Int, m: Int): Int {
        if (a >= n || b >= m) return inf
        if (idx == info.size) return a
        if (dp[idx][a][b] != -1) return dp[idx][a][b]
        dp[idx][a][b] = min(recur(a + info[idx][0], b, idx + 1, info, n, m), recur(a, b + info[idx][1], idx + 1, info, n, m))
        return dp[idx][a][b]
    }
    
    fun solution(info: Array<IntArray>, n: Int, m: Int): Int {
        val answer = recur(0, 0, 0, info, n, m)
        return if (answer == inf) -1 else answer
    }
}