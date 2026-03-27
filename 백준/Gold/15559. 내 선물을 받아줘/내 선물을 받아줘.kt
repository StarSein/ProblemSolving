class P15559 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, m) = readLine().split(" ").map { it.toInt() }
        val grid = Array(n) { readLine().toCharArray() }

        // 풀이
        val visited = Array(n) { BooleanArray(m) }
        val groupId = Array(n) { IntArray(m) { -1 } }
        var gId = 0
        var answer = 0
        for (r in 0..<n) {
            for (c in 0..<m) {
                if (visited[r][c]) {
                    continue
                }
                gId++

                var nr = r
                var nc = c

                while (!visited[nr][nc]) {
                    visited[nr][nc] = true
                    groupId[nr][nc] = gId

                    when (grid[nr][nc]) {
                        'E' -> { nc++ }
                        'W' -> { nc-- }
                        'S' -> { nr++ }
                        else -> { nr-- }
                    }
                }

                if (groupId[nr][nc] == gId) {
                    answer++
                }
            }
        }

        // 출력
        println(answer)
    }
}

fun main() = with(P15559()) {
    solve()
}