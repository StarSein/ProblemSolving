class P32197 {

    class Tuple(
        val node: Int,
        val t: Int,
        val count: Int
    ) {
        operator fun component1(): Int = node
        operator fun component2(): Int = t
        operator fun component3(): Int = count
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, m) = readLine().split(" ").map { it.toInt() }
        val graph = Array(n + 1) { mutableListOf<Pair<Int, Int>>() }
        repeat(m) {
            val (s, e, t) = readLine().split(" ").map { it.toInt() }
            graph[s].add(e to t)
            graph[e].add(s to t)
        }
        val (a, b) = readLine().split(" ").map { it.toInt() }

        // 풀이
        val dq = ArrayDeque<Tuple>()
        dq.add(Tuple(a, 0, 0))
        dq.add(Tuple(a, 1, 0))
        val visited = Array(n + 1) { BooleanArray(2) }

        while (dq.isNotEmpty()) {
            val (node, t, count) = dq.removeFirst()
            if (visited[node][t]) continue
            visited[node][t] = true

            // 정답 출력
            if (node == b) {
                println(count)
                break
            }

            for ((next, nt) in graph[node]) {
                if (visited[next][nt]) continue
                if (nt == t) dq.addFirst(Tuple(next, t, count))
                else dq.addLast(Tuple(next, nt, count + 1))
            }
        }
    }
}

fun main() = with(P32197()) {
    solve()
}