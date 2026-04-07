import kotlin.math.max

/**
 *
 *      0 1 2 3 4 5 6 7 8 9
 * min      1 1 1 4 4 4 4
 * max  1 9 4 4 9 9 9 9 9
 *
 *
 * 1. 이진 검색 트리를 그대로 구현하여 사용할 경우, 경사 트리가 만들어지는 경우가 최악의 경우가 되며,
 *    이때 시간 복잡도는 O(N^2)으로 N <= 250_000 을 커버할 수 없다
 * 2. 항상 오름차순으로 정렬되는 가상의 배열 A를 가정하자
 *    새로 삽입될 원소의 인덱스를 i라고 할 때 A[i-1]과 A[i+1]을 알면
 *    height of A[i] = max(height of A[i-1], height of A[i+1]) + 1 로 간단히 구할 수 있다
 * 3. 단, 어떻게 O(logN) 으로 가상의 배열 A의 원소 입력과 조회를 구현하느냐가 관건이다
 *    이때 최솟값 세그먼트 트리와 최댓값 세그먼트 트리를 이용하면 된다
 *    1) 이진 검색 트리에 새로 삽입될 원소를 x라고 하자
 *       x보다 작은 최대 원소를 left, 큰 최소 원소를 right라고 하면
 *       left = maxSeg[x]
 *       right = minSeg[x]
 *       height[x] = max(height[left], height[right])
 *    2) 이제 x의 삽입을 세그먼트 트리에 반영한다
 *       구간 [left+1, x-1]은 minSeg[i]가 x로 변경된다
 *       구간 [x+1, right-1]은 maxSeg[i]가 x로 변경된다
 *    3) 구간 업데이트, 점 쿼리 수행을 O(logN)으로 하기 위해 lazy propagation을 적용한다
 *    4) 구간 쿼리가 없으므로 internal node의 동기화 로직은 제외한다
 *
 */


class P1539 {

    var n = 0
    var pArr = intArrayOf()

    val maxN = 250_000
    val heights = IntArray(maxN + 2) { 0 }
    val mis = IntArray(4 * maxN) { maxN + 1 }
    val mas = IntArray(4 * maxN) { 0 }
    val mil = IntArray(4 * maxN) { 0 }
    val mal = IntArray(4 * maxN) { 0 }

    fun updateLazy(node: Int, s: Int, e: Int, seg: IntArray, lazy: IntArray) {
        if (lazy[node] != 0) {
            if (s != e) {
                lazy[node shl 1] = lazy[node]
                lazy[node shl 1 or 1] = lazy[node]
            }
            seg[node] = lazy[node]
            lazy[node] = 0
        }
    }

    fun updateSeg(node: Int, s: Int, e: Int, l: Int, r: Int, v: Int, seg: IntArray, lazy: IntArray) {
        updateLazy(node, s, e, seg, lazy)
        if (r < s || e < l) return
        if (l <= s && e <= r) {
            lazy[node] = v
            updateLazy(node, s, e, seg, lazy)
            return
        }
        val m = s + e shr 1
        updateSeg(node shl 1, s, m, l, r, v, seg, lazy)
        updateSeg(node shl 1 or 1, m + 1, e, l, r, v, seg, lazy)
    }

    fun query(node: Int, s: Int, e: Int, x: Int, seg: IntArray, lazy: IntArray): Int {
        updateLazy(node, s, e, seg, lazy)
        if (x !in s..e) return -1
        if (s == x && e == x) return seg[node]
        val m = s + e shr 1
        return max(
            query(node shl 1, s, m, x, seg, lazy),
            query(node shl 1 or 1, m + 1, e, x, seg, lazy)
        )
    }

    fun insert(x: Int) {
        val left = query(1, 1, n, x, mas, mal)
        val right = query(1, 1, n, x, mis, mil)

        heights[x] = max(heights[left], heights[right]) + 1

        updateSeg(1, 1, n, left + 1, x - 1, x, mis, mil)
        updateSeg(1, 1, n, x + 1, right - 1, x, mas, mal)
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        n = readLine().toInt()
        pArr = IntArray(n) { readLine().toInt() + 1 }

        // 풀이
        pArr.forEach { p -> insert(p) }
        var answer = 0L
        for (i in 1..n) {
            answer += heights[i]
        }

        // 정답
        println(answer)
    }

}


fun main() = with(P1539()) {
    solve()
}