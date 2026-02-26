import java.util.PriorityQueue

/**
 *
 * # 발견
 * 1. 세 번 건널 때마다 목초지에 있는 풀이 비용으로 추가되므로,
 *    마지막으로 풀을 먹고 길을 몇 번 건넌 시점인지가 중요한 상태 정보 중 하나가 된다.
 *    이를 반영하면 상태 정보는
 *    1) 현재 행
 *    2) 현재 열
 *    3) 마지막 식사 후 이동 횟수 < 3 [이동해서 3이 되는 시점에는 풀을 먹어야 한다]
 *    4) 소요 시간
 * 2. 노드의 개수는 N*N*3 인 그래프에서 최단 거리를 찾는 문제와 같다.
 *
 * # 접근
 * - 다익스트라
 * - 시간 O((N^2) * log(N^2))
 * - 공간 O(N^2)
 * 1. 출발점을 (0, 0)으로 하여 인접한 노드로 이동하며 우선순위 큐에 넣는다 (정렬 기준: 소요 시간의 오름차순)
 * 2. 우선순위 큐에서 뽑은 원소의 위치가 (N-1, N-1)이면 그때의 소요 시간을 정답으로 출력한다
 *
 */


class P14461 {

    class Node(
        val row: Int,
        val col: Int,
        val count: Int,
        val time: Long
    )

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, t) = readLine().split(" ").map(String::toInt)
        val board = Array(n) {
            readLine().split(" ").map(String::toInt).toIntArray()
        }

        // 풀이
        val dr = arrayOf(1, 0, -1, 0)
        val dc = arrayOf(0, 1, 0, -1)
        val visited = Array(n) { Array(n) { BooleanArray(3) { false } } }
        val pq = PriorityQueue(Comparator<Node> { o1, o2 -> if (o1.time > o2.time) 1 else -1 })
        pq.offer(Node(0, 0, 0, 0L))
        
        var answer = -1L
        while (pq.isNotEmpty()) {
            val cur = pq.poll()
            if (visited[cur.row][cur.col][cur.count]) continue
            visited[cur.row][cur.col][cur.count] = true

            if (cur.row == n-1 && cur.col == n-1) {
                answer = cur.time
                break
            }

            repeat(4) {
                val nrow = cur.row + dr[it]
                val ncol = cur.col + dc[it]
                if (nrow !in 0..<n || ncol !in 0..<n) return@repeat

                val ncount = (cur.count + 1) % 3
                if (visited[nrow][ncol][ncount]) return@repeat

                val ntime = cur.time + t + if (ncount == 0) board[nrow][ncol] else 0
                pq.offer(Node(nrow, ncol, ncount, ntime))
            }
        }

        // 출력
        println(answer)
    }

}

fun main() = with(P14461()) {
    solve()
}