/**
 *
 * 정체 구역 정보를 그대로 2차원 배열에 표현하면 시간 초과가 날 수 있다. (시간 복잡도가 O(NMK)가 되므로)
 * 모든 정체 구역 중점을 시작점으로 하여 너비 우선 탐색을 통해 중복 처리를 방지하자. -> 시간 복잡도 O(NM)
 * 이렇게 만들어진 2차원 배열 속에서 재헌이의 최소 이동 경로를 너비 우선 탐색으로 구하자. -> O(NM)
 *
 */


class P26009 {

    class Jam(
        val row: Int,
        val col: Int,
        val moveCnt: Int
    )

    fun solve() = with(System.`in`.bufferedReader()) {
        val dr = intArrayOf(0, 1, 0, -1)
        val dc = intArrayOf(1, 0, -1, 0)

        // 입력
        val (n, m) = readLine().split(" ").map(String::toInt)
        val k = readLine().toInt()
        val jams = List(k) { readLine().split(" ").map(String::toInt).let {
            Jam(it[0], it[1], it[2])
        } }

        // 풀이
        val isJam = Array(n + 1) { BooleanArray(m + 1) { false } }
        val dq0 = ArrayDeque(jams.sortedBy { -it.moveCnt })
        val dq1 = ArrayDeque<Jam>()
        dq0.removeFirst().let { dq1.add(it); isJam[it.row][it.col] = true }
        while (dq1.isNotEmpty()) {
            val cur = dq1.removeFirst()
            while (dq0.isNotEmpty() && dq0.first().moveCnt == cur.moveCnt) { 
                dq0.removeFirst().let { dq1.addFirst(it); isJam[it.row][it.col] = true }
            }
            if (cur.moveCnt == 0) continue
            for (d in 0..3) {
                val nr = cur.row + dr[d]
                val nc = cur.col + dc[d]
                if (nr in 1..n && nc in 1..m && !isJam[nr][nc]) {
                    isJam[nr][nc] = true
                    dq1.addLast(Jam(nr, nc, cur.moveCnt - 1))
                }
            }
        }

        val visited = Array(n + 1) { BooleanArray(m + 1) { false } }
        val dq2 = ArrayDeque<Pair<Int, Int>>()
        dq2.addLast(1 to 1)
        visited[1][1] = true
        var dist = 0
        while (dq2.isNotEmpty()) {
            dist++
            var sz = dq2.size
            while (sz-- > 0) {
                val (r, c) = dq2.removeFirst()
                for (d in 0..3) {
                    val nr = r + dr[d]
                    val nc = c + dc[d]
                    if (nr == n && nc == m) {
                        println("YES\n$dist")
                        return
                    }
                    if (nr in 1..n && nc in 1..m && !isJam[nr][nc] && !visited[nr][nc]) {
                        visited[nr][nc] = true
                        dq2.addLast(nr to nc)
                    }
                }
            }
        }
        println("NO")
    }
}


fun main() = P26009().solve()