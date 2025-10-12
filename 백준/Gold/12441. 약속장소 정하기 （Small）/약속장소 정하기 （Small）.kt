import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.PriorityQueue
import kotlin.math.max
import kotlin.math.min

/**
 *
 * 접근
 * 1. 역방향 다익스트라: O(T * (V+E)logV)
 * - 각각의 친구 -> 약속 장소 로 하게 되면 친구의 수만큼 곱절로 최단 거리 탐색을 해야 한다
 * - 반면, 약속 장소 -> 모든 친구 로 하게 되면, 단 한 번의 최단 거리 탐색으로 가장 먼 친구까지 거리의 최솟값을 구할 수 있다
 * - 각각의 노드를 약속 장소로 지정해 보고 한 번씩 최단 거리 탐색을 하면 된다
 * - 친구의 속력이 단위 시간당 거리로 나타나는 게 아니라, 거리 1당 시간으로 나타나며, 모든 간선에 대해 동일하게 적용되므로,
 *   (친구) - (약속 장소) 사이의 간선 개수가 최소이면 그게 곧 최단 거리가 된다.
 *   간선 개수에 친구의 속력을 곱하기만 하면 된다
 *
 * WA 사유
 * 1. 친구들이 서로 다른 도시에 위치한다는 보장이 없다.
 * -> 각 도시에 대해 단위 거리당 소요 시간의 최댓값을 저장하자.
 * -> 그런 경우 각 도시에 대해 친구 여부가 아니라, 친구 개수를 저장해야 한다.
 */


private class P12441 {

    val br = BufferedReader(InputStreamReader(System.`in`))
    val answers = arrayListOf<Int>()

    class Edge(
        val nextNode: Int,
        val dist: Int
    )

    class Route(
        val curNode: Int,
        val totalDist: Int
    )

    var T = 0
    var N = 0
    var P = 0
    var M = 0
    val numFriends = Array(111) { 0 }
    val speeds = Array(111) { 0 }
    val graph = Array(111) { ArrayList<Edge>() }

    fun readInput() {
        numFriends.fill(0)
        speeds.fill(0)
        graph.forEach { it.clear() }

        br.readLine().trim().split(" ").map(String::toInt).let { (n, p, m) ->
           N = n
           P = p
           M = m
        }

        repeat(P) {
            br.readLine().trim().split(Regex("\\s+")).map(String::toInt).let { (x, v) ->
                numFriends[x]++
                speeds[x] = max(speeds[x], v)
            }
        }

        repeat(M) {
            br.readLine().trim().split(Regex("\\s+")).map(String::toInt).let { tokens ->
                val D = tokens[0]
                val L = tokens[1]
                repeat(L - 1) {
                    val u = tokens[2 + it]
                    val v = tokens[3 + it]
                    graph[u].add(Edge(
                        nextNode = v,
                        dist = D
                    ))
                    graph[v].add(Edge(
                        nextNode = u,
                        dist = D
                    ))
                }
            }
        }
    }

    fun solve() {
        var answer = Int.MAX_VALUE
        (1..N).forEach { meetingPoint ->
            val visited = Array(N + 1) { false }
            val pq = PriorityQueue<Route>(Comparator.comparingInt { it.totalDist })
            pq.offer(Route(meetingPoint, 0))
            var meetingCount = 0
            var maxTime = 0
            while (pq.isNotEmpty()) {
                val route = pq.poll()
                if (visited[route.curNode]) {
                    continue
                }
                visited[route.curNode] = true
                if (numFriends[route.curNode] > 0) {
                    maxTime = max(maxTime, route.totalDist * speeds[route.curNode])
                    meetingCount += numFriends[route.curNode]
                }
                graph[route.curNode].forEach { edge ->
                    if (!visited[edge.nextNode]) {
                        pq.offer(Route(
                            curNode = edge.nextNode,
                            totalDist = route.totalDist + edge.dist
                        ))
                    }
                }
            }

            if (meetingCount == P) {
                answer = min(answer, maxTime)
            }
        }

        answers.add(
            if (answer == Int.MAX_VALUE) -1 else answer
        )
    }

    fun writeOutput() = with(StringBuilder()) {
        answers.forEachIndexed { i, answer ->
            append("Case #${i + 1}: ${answer}\n")
        }
        print(this.toString())
    }
}

fun main() = with(P12441()) {
    T = br.readLine().toInt()
    repeat(T) {
        readInput()
        solve()
    }
    writeOutput()
}