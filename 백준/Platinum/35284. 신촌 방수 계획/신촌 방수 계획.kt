import kotlin.math.min

/**
 *
 * # 발견
 * 1. 우산 보관함을 0개 설치하는 경우 MST를 구하는 문제와 같다
 * 2. 우산 보관함을 1개 설치하는 경우는 의미가 없다 (무조건 0개 설치하는 경우보다 손해다)
 * 3. 우산 보관함을 2개 이상 설치하는 경우의 최적해는
 *    우산 보관함 설치를 '가상의 노드와 연결'로 간주하고
 *    크기가 (N+1)인 MST를 구하는 문제와 같다
 *    * MST가 만들어지는 과정에서는 임의의 두 노드에 우산 보관함을 설치한다고 해서
 *      항상 두 노드가 연결되는 것은 아니다 (= 비를 맞지 않고 이동 가능한 것은 아니다)
 *      단, MST가 만들어지고 나면 MST를 이루는 다른 간선(지붕을 설치한 통로)을 통해
 *      우산을 가지게 되는 두 노드가 인접한 지점이 만들어진다
 *
 * # 접근
 * - 크루스칼 알고리즘 (MST를 두 번 해야 하는데, 이때 구현의 간소화를 위해)
 * - 시간 O(MlogM)
 * - 공간 O(N)
 * 1. 우산 보관함을 설치하지 않는 경우의 MST를 구한다
 * 2. 설치하는 경우의 MST를 구한다
 * 3. 둘 중 최솟값을 출력한다
 *
 */


class P35284 {

    class Edge(
        val u: Int,
        val v: Int,
        val w: Int
    )

    fun union(u: Int, v: Int, parents: IntArray): Boolean {
        val pu = getParent(u, parents)
        val pv = getParent(v, parents)

        if (pu == pv) return false

        if (pu <= pv) {
            parents[pv] = pu
        } else {
            parents[pu] = pv
        }
        return true
    }

    fun getParent(x: Int, parents: IntArray): Int {
        if (parents[x] == x) return x
        return getParent(parents[x], parents).also { parents[x] = it }
    }

    fun getMST(nodeRange: IntRange, edges: List<Edge>): Long {
        var mst = 0L

        val parents = IntArray(nodeRange.last + 1) { it }
        val sortedEdges = edges.sortedBy { it.w }

        sortedEdges.forEach { edge ->
            if (union(edge.u, edge.v, parents)) {
                mst += edge.w
            }
        }

        return mst
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, m) = readLine().split(" ").map(String::toInt)
        val cArr = readLine().split(" ").map(String::toInt)
        val edges = mutableListOf<Edge>()
        repeat(m) {
            val (u, v, w) = readLine().split(" ").map(String::toInt)
            edges.add(Edge(u, v, w))
        }

        // 풀이
        val res1 = getMST(
            nodeRange = 1..n,
            edges = edges
        )
        cArr.forEachIndexed { i, c -> edges.add(Edge(0, i + 1, c)) }
        val res2 = getMST(
            nodeRange = 0..n,
            edges = edges
        )
        val answer = min(res1, res2)

        // 출력
        println(answer)
    }

}

fun main() = with(P35284()) {
    solve()
}