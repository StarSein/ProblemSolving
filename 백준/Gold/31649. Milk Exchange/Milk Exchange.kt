import java.util.PriorityQueue

/**
 *
 * ex1)
 * R R L
 * - 1 0
 *
 * 1 1 1
 * 0 1 1
 *
 * ex3)
 * R R R L R R L L R
 * 0 0 1 0 - 1 1 - -
 *
 * 5 8 4 9 3 4 9 5 4 - 0th
 * 5 8 4 9 0 4 9 2 1 - 3th
 *
 * R R R L X R L L R
 * 0 0 1 0 x 0 1 - -
 * 5 8 4 9 0 4 9 1 0 - 4th
 *
 * R R R L X R L L X
 * - 0 1 0 x 0 1 - x
 * 4 8 4 9 0 4 9 0 0 - 5th
 *
 * R R R L X R L X X
 * - 0 1 0 x 0 0 x x
 * 3 8 4 9 0 4 9 0 0 - 6th
 *
 * # 분석
 * 1. 단방향 그래프로 나타내면, 각 소의 바구니가 노드가 되고 'L'과 'R'이 방향을 갖는 간선이 된다.
 * 이 그래프에서 사이클을 이루는 노드는 영원히 Capa를 일정하게 유지한다.
 * 반면 그렇지 않은 노드는 in-degree가 0인 노드부터 고갈되기 시작한다.
 * 2. 입력의 특성상, 모든 노드에 대해 in-degree는 2 이하이다.
 * 3. 사이클을 하나의 노드로 통합하게 되면, 그때부터는 모든 노드에 대해 in-degree는 1 이하이다.
 *
 * # 접근
 * 1. 위상 정렬 - O(N)
 *  (1) 사이클을 찾을 필요 없이, 각 노드의 in-degree 개수를 추적하면 된다
 *  (2) in-degree가 0인 노드를 원소로 하는 Priority Queue를 이용한다
 *    - 원소는 (노드 번호 i, 고갈 시점 t)를 저장한다
 *    - 이때 정렬 기준은 t의 오름차순이다
 *    - 마지막으로 고갈된 노드 i의 indegree를 j라고 할 때,
 *      t_i = t_j + a_i
 *  (3) M을 초과하지 않는 범위 내에서 반복을 통해, 고갈되는 노드를 모두 처리한다
 *  (4) in-degree가 0이면서 아직 고갈 안 된 노드의 a 값을 M 시점에 맞게 갱신한다
 *  (5) 아직 고갈 안 된 노드의 a 값 총합을 정답으로 출력한다
 */


class P31649 {

    var N = 0
    var M = 0
    var s = charArrayOf()
    var a = intArrayOf()
    var answer = 0L

    class Node(
        val i: Int,
        val t: Int
    )

    fun getNextNode(i: Int): Int {
        return if (s[i] == 'L') (i - 1 + N) % N else (i + 1) % N
    }

    fun readInput() = with(System.`in`.bufferedReader()) {
        readLine().split(" ").map { it.toInt() }.let {
            N = it[0]
            M = it[1]
        }
        s = readLine().toCharArray()
        a = readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    fun solve() {
        // indegree 배열을 만든다
        val indegree = IntArray(N)
        repeat(N) {
            getNextNode(it).let { j ->
                indegree[j]++
            }
        }

        // indegree 가 0인 원소를 우선순위 큐에 넣는다
        val pq = PriorityQueue<Node> { o1, o2 -> o1.t - o2.t }
        indegree.forEachIndexed { i, idgr ->
            if (idgr == 0) {
                pq.offer(Node(i, a[i]))
            }
        }

        // M 시점 이전까지 고갈 처리와 전이를 반복한다
        while (pq.isNotEmpty()) {
            val cur = pq.peek()
            if (cur.t > M) {
                break
            }
            pq.poll()
            a[cur.i] = 0

            getNextNode(cur.i).let { j ->
                if (--indegree[j] == 0) {
                    pq.offer(Node(j, cur.t + a[j]))
                }
            }
        }

        // indegree 가 0이면서 아직 고갈 안 된 노드의 a 값을 M 시점에 맞게 갱신한다
        while (pq.isNotEmpty()) {
            val cur = pq.poll()
            a[cur.i] = cur.t - M
        }

        // 아직 고갈 안 된 노드의 a 값 총합을 정답으로 출력한다
        a.forEach {
            answer += it
        }
    }

    fun writeOutput() {
        println(answer)
    }
}

fun main() = with(P31649()) {
    readInput()
    solve()
    writeOutput()
}