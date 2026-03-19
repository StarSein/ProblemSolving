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
        val isNodeVisited = BooleanArray(n + 1)
        val isTubeVisited = BooleanArray(m)
        dq.addLast(1)
        isNodeVisited[1] = true
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
                    if (isTubeVisited[i]) continue
                    isTubeVisited[i] = true

                    for (next in tubes[i]) {
                        if (isNodeVisited[next]) continue
                        dq.addLast(next)
                        isNodeVisited[next] = true
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