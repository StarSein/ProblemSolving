class P5214 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, k, m) = readLine().split(" ").map(String::toInt)
        val nodes = Array(n + 1) { mutableListOf<Int>()}
        val tubes = Array(m) { intArrayOf() }
        repeat(m) {
            val tube = readLine().split(" ").map(String::toInt).toIntArray()
            tubes[it] = tube
            tube.forEach { node -> nodes[node].add(it) }
        }

        // 풀이
        val dq = ArrayDeque<Int>()
        val visited = BooleanArray(n + 1)
        dq.addLast(1)
        visited[1] = true
        var dist = 0
        while (dq.isNotEmpty()) {
            dist++
            repeat(dq.size) {
                val cur = dq.removeFirst()
                if (cur == n) {
                    println(dist)
                    return
                }
                for (i in nodes[cur]) {
                    for (next in tubes[i]) {
                        if (visited[next]) continue
                        dq.addLast(next)
                        visited[next] = true
                    }
                }
            }
        }
        println(-1)
    }
}


fun main() = with(P5214()) {
    solve()
}