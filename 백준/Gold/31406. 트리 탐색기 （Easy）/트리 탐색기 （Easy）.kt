import kotlin.math.max
import kotlin.math.min

/**
 *
 * 요구사항을 naive하게 구현하지 않아도 된다.
 * 커서는 처음에 dfs의 1번째 방문 노드에 위치한다.
 * move(k)를 하게 되면 dfs의 (1+k)번째 방문 노드를 출력하면 되고,
 * 다시 move(k')를 하게 되면 dfs의 (1+k+k')번째 방문 노드를 출력하면 된다.
 * 단, dfs에서 접힌 노드는 자식 노드 탐색을 건너뛴다.
 * 시간 복잡도 O(NQ)로 풀이된다.
 * 단, NQ <= 4_000_000 으로 재귀 호출 오버헤드가 유의미하게 크다.
 * dfs는 비재귀로 스택을 사용하여 구현하자.
 *
 */


class P31406 {

    val maxN = 2000
    val tree = Array(maxN + 1) { emptyList<Int>() }
    val isFolded = BooleanArray(maxN + 1) { true }

    fun dfs(): List<Int> {
        val ret = mutableListOf<Int>()
        val dq = ArrayDeque<Int>()
        dq.addAll(tree[1])
        while (dq.isNotEmpty()) {
            val cur = dq.removeLast()
            ret.add(cur)
            if (isFolded[cur]) continue
            dq.addAll(tree[cur])
        }
        return ret
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, q) = readLine().split(" ").map { it.toInt() }
        for (node in 1..n) {
            val arr = readLine().split(" ").map { it.toInt() }
            tree[node] = arr.slice(1..arr.lastIndex).reversed()
        }

        // 풀이
        var cursor = tree[1].last()
        var preOrder = emptyList<Int>()
        var outDated = true
        val sb = StringBuilder()
        repeat(q) {
            val query = readLine().split(" ")
            if (query[0] == "toggle") {
                isFolded[cursor] = !isFolded[cursor]
                outDated = true
            } else {
                val k = query[1].toInt()
                if (outDated) {
                    preOrder = dfs()
                    outDated = false
                }
                val ptr = preOrder.indexOf(cursor)
                val nptr = if (k > 0) min(preOrder.size - 1, ptr + k)
                           else max(0, ptr + k)
                cursor = preOrder[nptr]
                sb.append(cursor).append('\n')
            }
        }

        // 출력
        print(sb)
    }
}


fun main() = with(P31406()) {
    solve()
}