class P13265 {


    fun solve() = with(System.`in`.bufferedReader()) {
        val t = readLine().toInt()
        val sb = StringBuilder()
        repeat(t) {
            // 테케별 입력
            val (n, m) = readLine().split(" ").map { it.toInt() }
            val graph = Array(n + 1) { mutableListOf<Int>() }
            repeat(m) {
                val (x, y) = readLine().split(" ").map { it.toInt() }
                graph[x].add(y)
                graph[y].add(x)
            }

            // 테케별 풀이
            val dq = ArrayDeque<Int>()
            val colors = IntArray(n + 1) { 0 }
            var possible = true
            outer@ for (node in 1..n) {
                if (colors[node] != 0) continue

                dq.addLast(node)
                var color = 1
                colors[node] = color
                while (dq.isNotEmpty()) {
                    color *= -1
                    val sz = dq.size
                    for (i in 0..<sz) {
                        val cur = dq.removeFirst()
                        for (next in graph[cur]) {
                            if (colors[next] == 0) {
                                colors[next] = color
                                dq.addLast(next)
                            } else if (colors[next] == -color) {
                                possible = false
                                break@outer
                            }
                        }
                    }
                }
            }
            val answer = if (possible) "possible\n" else "impossible\n"
            sb.append(answer)
        }

        // 출력
        print(sb)
    }
}


fun main() = with(P13265()) {
    solve()
}