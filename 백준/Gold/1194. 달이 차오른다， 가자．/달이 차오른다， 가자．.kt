/**
 *
 * # 접근
 * - 너비 우선 탐색
 * 1. 6가지 열쇠 소지 여부를 6개의 비트로 표현한다.
 * 2. N*M*(2^6)에 해당하는 노드를 상정하고 너비 우선 탐색을 한다.
 *
 */


class P1194 {

    class Node(
        val row: Int,
        val col: Int,
        val bit: Int
    )

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, m) = readLine().split(" ").map(String::toInt)
        val maze = Array(n) { readLine().toCharArray() }

        // 풀이
        var (sr, sc) = arrayOf(-1, -1)
        repeat(n) { r ->
            repeat(m) { c ->
                if (maze[r][c] == '0') {
                    sr = r
                    sc = c
                }
            }
        }
        var answer = -1
        val dr = arrayOf(1, 0, -1, 0)
        val dc = arrayOf(0, 1, 0, -1)
        val visited = Array(n) { Array(m) { BooleanArray(1 shl 6) { false } } }
        val dq = ArrayDeque<Node>()
        dq.add(Node(sr, sc, 0))
        visited[sr][sc][0] = true
        var dist = 1
        mainLoop@ while (dq.isNotEmpty()) {
            val size = dq.size
            for(i in 0..<size) {
                val cur = dq.removeFirst()
                for (j in 0..<4) {
                    val nr = cur.row + dr[j]
                    val nc = cur.col + dc[j]
                    if (nr !in 0..<n || nc !in 0..<m) {
                        continue
                    }
                    val nextVal = maze[nr][nc]
                    if (nextVal == '1') {
                        answer = dist
                        break@mainLoop
                    }
                    val nb = if (nextVal in 'a'..'f') cur.bit or (1 shl (nextVal - 'a'))
                    else cur.bit
                    if (nextVal in 'A'..'F' && (cur.bit and (1 shl (nextVal - 'A'))) == 0) {
                        continue
                    }
                    if (nextVal == '#') {
                        continue
                    }
                    if (visited[nr][nc][nb]) {
                        continue
                    }
                    dq.addLast(Node(nr, nc, nb))
                    visited[nr][nc][nb] = true
                }
            }
            dist++
        }

        // 출력
        println(answer)
    }
}


fun main() = with(P1194()) {
    solve()
}