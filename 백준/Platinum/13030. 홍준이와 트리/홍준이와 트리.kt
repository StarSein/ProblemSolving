/**
 *
 * - 오일러 경로 테크닉으로 트리의 노드들에 관한 정보를 세그먼트 트리에 저장한다
 *   이때 세그먼트 트리의 각 노드는 Pair<Long, Long>
 * - 유형 1 쿼리에 대해, in[v] 부터 out[v] 까지 (x + h[v] * k, k) 를 더한다.
 * - 유형 2 쿼리에 대해, 세그먼트 트리의 리프 노드에서 p.first - h[v] * p.second 를 반환한다
 *
 */


class P13030 {

    val mod = 1_000_000_007L
    val maxN = 300_001
    val tree = Array(maxN) { mutableListOf<Int>() }
    val inCount = Array(maxN) { 0 }
    val outCount = Array(maxN) { 0 }
    val height = Array(maxN) { 0 }
    var traverseCount = 0

    val sz = 4 * maxN
    val zeroPair = 0L to 0L
    val seg = Array(sz) { zeroPair }
    val lazy = Array(sz) { zeroPair }

    operator fun Pair<Long, Long>.plus(e: Pair<Long, Long>): Pair<Long, Long> {
        return (first + e.first) % mod to (second + e.second) % mod
    }

    fun dfs(node: Int, h: Int) {
        height[node] = h
        inCount[node] = ++traverseCount
        for (child in tree[node]) {
            dfs(child, h + 1)
        }
        outCount[node] = traverseCount
    }

    fun traverse() {
        dfs(1, 1)
    }

    fun updateSeg(node: Int, s: Int, e: Int, l: Int, r: Int, v: Pair<Long, Long>) {
        updateLazy(node, s, e)
        if (r < s || e < l) return
        if (l <= s && e <= r) {
            lazy[node] += v
            updateLazy(node, s, e)
            return
        }
        val m = s + e shr 1
        updateSeg(node shl 1, s, m, l, r, v)
        updateSeg(node shl 1 or 1, m + 1, e, l, r, v)

        // 구간 쿼리는 없으므로 세그먼트 트리의 단말 노드에 대한 동기화는 하지 않는다.
    }

    fun updateLazy(node: Int, s: Int, e: Int) {
        if (lazy[node] != zeroPair) {
            if (s != e) {
                lazy[node shl 1] += lazy[node]
                lazy[node shl 1 or 1] += lazy[node]
            }

            seg[node] += lazy[node]

            lazy[node] = zeroPair
        }
    }

    fun query(node: Int, s: Int, e: Int, tgt: Int): Pair<Long, Long> {
        updateLazy(node, s, e)
        if (tgt !in s..e) return zeroPair
        if (s == e && s == tgt) return seg[node]
        val m = s + e shr 1
        return query(node shl 1, s, m, tgt) + query(node shl 1 or 1, m + 1, e, tgt)
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 트리 입력 처리
        val (n, q) = readLine().split(" ").map { it.toInt() }
        readLine().split(" ").map { it.toInt() }
            .forEachIndexed { i, p ->
                tree[p].add(i + 2)
            }

        // 전처리
        traverse()

        // 쿼리 입력 처리
        val sb = StringBuilder()
        repeat(q) {
            val inp = readLine().split(" ").map { it.toInt() }
            if (inp[0] == 1) {
                val (v, x, k) = inp.slice(1..3)
                val pair = (height[v].toLong() * k + x) % mod to k.toLong()
                updateSeg(1, 1, n, inCount[v], outCount[v], pair)
            } else {
                val v = inp[1]
                val pair = query(1, 1, n, inCount[v])
                val res = ((pair.first - height[v] * pair.second) % mod + mod) % mod
                sb.append(res).append('\n')
            }
        }

        // 출력
        print(sb)
    }
}


fun main() = with(P13030()) {
    solve()
}