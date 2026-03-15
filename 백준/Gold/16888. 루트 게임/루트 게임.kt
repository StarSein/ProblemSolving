class P16888 {

    val nums = (1..1000).map { it * it }
    val dp = Array(1_000_001) { -1 }
    // dp[i]: 자기 턴이 시작됐을 때 수가 i인 경우 이길 수 있으면 1, 그렇지 않으면 0

    fun recur(i: Int): Int {
        if (dp[i] != -1) return dp[i]
        if (i == 0) return 0
        for (x in nums) {
            if (x > i) break
            if (recur(i - x) == 0) return 1.also { dp[i] = 1 }
        }
        return 0.also { dp[i] = 0 }
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        repeat(dp.size) { recur(it) } // 재귀 호출 스택이 너무 깊어져서 시간 초과가 발생하는 것을 방지하기 위함
        val t = readLine().toInt()
        val sb = StringBuilder()
        repeat(t) {
            val n = readLine().toInt()
            val answer = if (recur(n) == 1) "koosaga" else "cubelover"
            sb.append(answer).append('\n')
        }
        print(sb)
    }
}


fun main() = with(P16888()) {
    solve()
}