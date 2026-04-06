import kotlin.math.max
import kotlin.math.min

/**
 *
 * 1. 나무 배열을 높이의 오름차순으로 정렬한다.
 * 2. 정렬된 배열을 세그먼트 트리로 나타낸다.
 *    각 노드는 해당 구간에 존재하는 나무 높이의 최댓값을 저장한다.
 * 3. F 작업에 대해,
 *  1) 이분 탐색으로 나무 높이가 H 이상인 인덱스의 최솟값 l을 구한다.
 *  2) 일단 구간 [l, l+C-1]에 +1 만큼 update를 하게 된다.
 *     그런데 이 경우 기존의 정렬 상태가 깨질 수 있다.
 *     l+C-1에 위치한 나무 높이를 H' 이라고 할 때,
 *     높이가 H'인 나무가 그 오른쪽에도 위치하면 정렬이 깨진다.
 *     이를 막기 위해서는, 나무 높이가 H'+1 이상인 인덱스의 최솟값 r을 구한다.
 *     그리고 나무 높이가 H' 이상인 인덱스의 최솟값 m을 구한다.
 *     구간 [m, r-1]이 나무 높이가 H'인 것이다.
 *     높이가 H'인 나무 중 update 대상이 되는 나무의 개수는 l+C-m 이다.
 *     결국 update 대상 구간은 둘로 나뉜다.
 *     [l, m-1] 그리고 [r-l-C+m, r-1] 이다. ( H < H' 인 경우 )
 *  3) H == H' 인 경우에는 l == m 이므로 [l, m-1]은 무시된다.
 * 4. C 작업에 대해,
 *  1) 이분 탐색으로 나무 높이가 min 이상인 인덱스의 최솟값 l을 구한다.
 *  2) 이분 탐색으로 나무 높이가 max+1 이상인 인덱스의 최솟값 r을 구한다.
 *  3) (r-l)의 값을 출력한다.
 */


class P2426 {

    var n = 0
    var m = 0
    val maxN = 100_001
    var heights = intArrayOf()
    val seg = IntArray(4 * maxN) { 0 }
    val lazy = IntArray(4 * maxN) { 0 }

    fun initSeg(node: Int, s: Int, e: Int) {
        if (s == e) {
            seg[node] = heights[s-1]
            return
        }
        val m = s + e shr 1
        initSeg(node shl 1, s, m)
        initSeg(node shl 1 or 1, m + 1, e)
        seg[node] = max(seg[node shl 1], seg[node shl 1 or 1])
    }

    fun updateSeg(node: Int, s: Int, e: Int, l: Int, r: Int, v: Int) {
        updateLazy(node, s, e)
        if (r < s || e < l) return
        if (l <= s && e <= r) {
            lazy[node] = v
            updateLazy(node, s, e)
            return
        }
        val m = s + e shr 1
        updateSeg(node shl 1, s, m, l, r, v)
        updateSeg(node shl 1 or 1, m + 1, e, l, r, v)
        seg[node] = max(seg[node shl 1], seg[node shl 1 or 1])
    }

    fun updateLazy(node: Int, s: Int, e: Int) {
        if (lazy[node] != 0) {
            if (s != e) {
                lazy[node shl 1] += lazy[node]
                lazy[node shl 1 or 1] += lazy[node]
            }
            seg[node] += lazy[node]

            lazy[node] = 0
        }
    }

    // 갱신된 heights[tgt]를 반환함
    fun query(node: Int, s: Int, e: Int, tgt: Int): Int {
        updateLazy(node, s, e)
        if (tgt !in s..e) return -1
        if (s == tgt && e == tgt) {
            return seg[node]
        }
        val m = s + e shr 1
        return max(
            query(node shl 1, s, m, tgt),
            query(node shl 1 or 1, m + 1, e, tgt)
        )
    }

    // heights[i] >= v 를 만족하는 i의 최솟값을 반환함
    fun binarySearch(node: Int, s: Int, e: Int, v: Int): Int {
        updateLazy(node, s, e)
        if (s == e) return s
        val m = s + e shr 1
        updateLazy(node shl 1, s, m)
        val leftMaxH = seg[node shl 1]
        return if (leftMaxH >= v) binarySearch(node shl 1, s, m, v)
               else binarySearch(node shl 1 or 1, m + 1, e, v)
    }

    fun taskF(c: Int, h: Int) {
        val tallestH = query(1, 1, n, n)
        if (tallestH < h) return
        val l = binarySearch(1, 1, n, h)
        val x = min(n, l + c - 1)
        val h2 = query(1, 1, n, x)
        val m = binarySearch(1, 1, n, h2)
        val r = if (query(1, 1, n, n) <= h2) n + 1
                else binarySearch(1, 1, n, h2 + 1)
        updateSeg(1, 1, n, l, m - 1, 1)
        val targetCount = x - l + 1
        val leftCount = m - l
        val rightCount = targetCount - leftCount
        updateSeg(1, 1, n, r - rightCount, r - 1, 1)
    }

    fun taskC(minH: Int, maxH: Int): Int {
        val tallestH = query(1, 1, n, n)
        if (tallestH < minH) return 0
        val l = binarySearch(1, 1, n, minH)
        val r = if (tallestH <= maxH) n + 1
                else binarySearch(1, 1, n, maxH + 1)

        return r - l
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 나무 정보 입력 처리
        readLine().split(" ").map { it.toInt() }.let {
            n = it[0]
            m = it[1]
        }
        heights = readLine().split(" ").map { it.toInt() }
            .sorted()
            .toIntArray()

        // 전처리
        initSeg(1, 1, n)

        // 해야 할 일 입력 처리
        val sb = StringBuilder()

        repeat(m) {
            val inp = readLine().split(" ")

            if (inp[0] == "F") {
                taskF(inp[1].toInt(), inp[2].toInt())
            } else {
                val res = taskC(inp[1].toInt(), inp[2].toInt())
                sb.append(res).append('\n')
            }
        }

        // 결과 출력
        print(sb)
    }
}


fun main() = with(P2426()) {
    solve()
}