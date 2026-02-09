import kotlin.collections.filter

/**
 *
 * # 접근
 * 1. BFS - O(N)
 * 친구 관계는 가중치가 없는 그래프로 표현된다.
 * 쿼리로 들어오는 두 노드 사이의 최단 거리를 너비 우선 탐색으로 구한다.
 *
 */

class P14588 {

    var N = 300
    var lines = emptyArray<Pair<Int, Int>>()
    var Q = 300
    var queries = emptyArray<Pair<Int, Int>>()
    var answers = emptyArray<Int>()

    fun readInput() = with(System.`in`.bufferedReader()) {
        N = readLine().toInt()
        lines = Array(N) {
            val (l, r) = readLine().split(" ").map { it.toInt() }
            Pair(l, r)
        }
        Q = readLine().toInt()
        queries = Array(Q) {
            val (a, b) = readLine().split(" ").map { it.toInt() }
            Pair(a-1, b-1)
        }
    }

    fun solve() {
        // 친구 관계를 그래프로 나타낸다
        val graph = Array(N) { a ->
            val lineA = lines[a]
            (0..<N).filter { b ->
                val lineB = lines[b]
                (a == b || lineA.second < lineB.first || lineB.second < lineA.first).not()
            }
        }

        // 각각의 쿼리에 대해 너비 우선 탐색으로 최단 거리를 정답으로 저장한다
        val dq = ArrayDeque<Int>()
        val dist = Array(N) { -1 }
        answers = queries.map { (a, b) ->
            dq.clear()
            dist.fill(-1)

            dq.addLast(a)
            dist[a] = 0
            while (dq.isNotEmpty()) {
                val cur = dq.removeFirst()
                graph[cur].forEach { next ->
                    if (dist[next] == -1) {
                        dq.add(next)
                        dist[next] = dist[cur] + 1
                    }
                }
            }

            dist[b]
        }.toTypedArray()
    }

    fun writeOutput() {
        // 정답을 순서대로 출력한다
        answers.forEach(::println)
    }
}

fun main() = with(P14588()) {
    readInput()
    solve()
    writeOutput()
}