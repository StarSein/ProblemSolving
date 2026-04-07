class P33943 {

    val axis = 10_000

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, m) = readLine().split(" ").map { it.toInt() }
        val pArr = if (n == 0) intArrayOf() else readLine().split(" ").map { it.toInt() }.toIntArray()

        // 풀이
        val target = m + axis
        val visited = BooleanArray(2 * axis + 1) { false }
        val dq = ArrayDeque<Int>()
        dq.addLast(axis)

        var answer = -1
        var coinCount = 0
        outer@ while (dq.isNotEmpty()) {
            var sz = dq.size
            while (sz-- > 0) {
                val cur = dq.removeFirst()
                if (cur == target) {
                    answer = coinCount
                    break@outer
                }
                for (p in pArr) {
                    val next = cur + p
                    if (next in visited.indices && !visited[next]) {
                        visited[next] = true
                        dq.addLast(next)
                    }
                }
            }
            coinCount++
        }

        // 출력
        println(answer)
    }
}


fun main() = with(P33943()) {
    solve()
}