class P28449 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val maxVal = 100_000
        val (n, m) = readLine().split(" ").map { it.toInt() }
        val a = readLine().split(" ").map { it.toInt() }.toIntArray()
        val b = readLine().split(" ").map { it.toInt() }.toIntArray()

        // 풀이
        val psb = IntArray(maxVal + 1)
        b.forEach { psb[it]++ }
        for (i in 1..psb.lastIndex) {
            psb[i] += psb[i - 1]
        }

        var winCount = 0L
        var drawCount = 0L
        a.forEach { winCount += psb[it-1]; drawCount += psb[it] - psb[it-1] }

        // 출력
        val loseCount = n.toLong() * m - (winCount + drawCount)
        val answer = "$winCount $loseCount $drawCount"
        println(answer)
    }
}


fun main() = with(P28449()) {
    solve()
}