import java.util.PriorityQueue
import java.util.StringTokenizer

/**
 *
 * # 발견
 * 총 수금액 최소화 조건과 최대 수금액 최소화 조건이 혼재되어 있다.
 * 둘 다 동시에 최소화하는 최적 선택은 불가능하다.
 * 따라서 둘 중 하나는 임의의 제약조건으로서 설정하고, 그 제약조건 안에서 나머지 하나를 최소화하는 최적 선택을 해야 한다.
 * 총 수금액을 최소화하는 것은 다익스트라를 이용하면 되므로, 최대 수금액을 임의의 제약조건으로 설정하자.
 * 최대 수금액이 선형성을 띠기 때문에 매개 변수 탐색을 통해, 최대 수금액의 최솟값을 찾을 수 있다.
 *
 * # 접근
 * - 매개 변수 탐색, 다익스트라
 * - 시간 복잡도 O(TMlogN) (T = log(10^9))
 * - 공간 복잡도 O(N+M)
 * 1. 이분 탐색을 통해, 최대 수금액으로 달성 가능한 최솟값을 찾는다.
 *   1) 달성 가능 여부는 주어진 최대 수금액 제약조건 아래에서, C 이하의 총 수금액으로 도착지까지 도달 가능 여부다.
 *   2) 다익스트라를 통해, 총 수금액을 최소화하는 경로를 찾는다.
 *   3) 경로의 길이가 C 이하이면 가능이다.
 */


class P20183 {

    class Edge(
        val next: Int,
        val cost: Int
    )

    class Node(
        val node: Int,
        val totalCost: Long
    )

    fun isOk(costLimit: Int, totalCostLimit: Long, start: Int, end: Int, graph: Array<MutableList<Edge>>): Boolean {
        val pq = PriorityQueue<Node> { e1, e2 -> if (e1.totalCost >= e2.totalCost) 1 else -1 }
        val visited = BooleanArray(graph.size)

        pq.offer(Node(start, 0L))
        while (pq.isNotEmpty()) {
            val cur = pq.poll()
            if (visited[cur.node]) continue
            visited[cur.node] = true

            if (cur.node == end) {
                return cur.totalCost <= totalCostLimit
            }

            for (edge in graph[cur.node]) {
                if (visited[edge.next]) continue
                if (edge.cost > costLimit) continue
                pq.offer(Node(edge.next, cur.totalCost + edge.cost))
            }
        }

        return false
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val st = StringTokenizer(readLine(), " ")
        val n = st.nextToken().toInt()
        val m = st.nextToken().toInt()
        val a = st.nextToken().toInt()
        val b = st.nextToken().toInt()
        val c = st.nextToken().toLong()
        val graph = Array(n+1) { mutableListOf<Edge>() }
        repeat(m) {
            val (u, v, w) = readLine().split(" ").map(String::toInt)
            graph[u].add(Edge(v, w))
            graph[v].add(Edge(u, w))
        }

        // 풀이
        var answer = -1
        var s = 1
        var e = 1_000_000_000
        while (s <= e) {
            val mid = (s + e) / 2
            if (isOk(mid, c, a, b, graph)) {
                answer = mid
                e = mid - 1
            } else {
                s = mid + 1
            }
        }

        // 출력
        println(answer)
    }

}

fun main() = with(P20183()) {
    solve()
}