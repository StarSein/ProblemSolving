import kotlin.math.max
import kotlin.math.min

/**
 *
 * 1. 오일러 경로 테크닉으로 트리 구조를 세그먼트 트리의 단말 노드로 변환
 * 2. 느리게 갱신되는 세그먼트 트리 구현
 *  1) 각 노드는 (구간의 최솟값, 해당 최솟값을 가진 노드의 개수)를 저장
 *     - 이 최솟값은 파일 탐색기의 루트 노드로부터 해당 노드까지의 경로상에 접힌 노드(폴더)의 개수
 *     - 이 값이 0이어야 자신의 조상 노드가 모두 펼쳐져 있어, 파일 탐색기상에 해당 폴더가 나타나 있는 것임
 *  2) i번 폴더가 접혔다고 하면, 자신을 제외한 서브트리의 모든 노드들에 대해 값을 +1 해 줘야 함
 *     반대로 펼쳐진 경우에는 -1 해 줘야 함
 *     세그먼트 트리 상에서 구간 in[i]+1 부터 out[i] 까지 update() 호출하면 됨
 *  3) 세그먼트 트리 상의 두 개의 구간에 저장된 정보를 병합하는 방식
 *     두 구간 중 최솟값이 낮은 것의 정보를 채택
 *     두 구간의 최솟값이 같을 경우 노드의 개수를 합산
 * 3. 세그먼트 트리에서 커서가 위치한 노드 탐색
 *  1) 왼쪽 자식에서 접힌 폴더의 개수 count가 target보다 적으면 오른쪽 자식으로 이동, 그렇지 않으면 왼쪽 자식으로 이동
 *  2) 오른쪽 자식으로 이동할 때에는 target -= count 처리하자
 * 4. 커서의 위치 관리
 *  1) 2 <= cursor <= query(left=1, right=n) (query(s, e)는 구간 [s, e]에서 파일 탐색기에 노출되는 폴더의 개수, 1번 폴더 포함)
 *    를 항상 유지하자
 * 5. toggle 함수 구현
 *  1) cursor가 위치한 폴더를 i라고 할 때, update(left = in[i]+1, right = out[i], +-1) -> 각 폴더의 접힘 여부 관리 필요
 *  2) outdated = true 처리
 * 6. move 함수 구현
 *  1) outdated == true 인 경우,
 *     query(1, n)을 통해 보이는 폴더 개수 t를 갱신한 뒤
 *     outdated == false 처리함
 *  2) cursor += k 하고 2 <= cursor <= t 가 되도록 함
 *  3) binarySearch 함수를 통해 cursor가 위치한 폴더의 번호를 반환함
 *
 */


class P31402 {

    var n = 0
    var q = 0
    val maxN = 200_001
    val tree = Array(maxN) { emptyList<Int>() }
    val isFolded = Array(maxN) { false }
    var cursor = 0
    var curFolder = 0

    val inCount = Array(maxN) { 0 }
    val outCount = Array(maxN) { 0 }
    val inFolder = Array(maxN) { 0 }
    var traverseCount = 0

    val seg = Array(4 * maxN) { 0 to 0 }
    val lazy = Array(4 * maxN) { 0 }

    fun dfs(folder: Int) {
        inCount[folder] = ++traverseCount
        inFolder[traverseCount] = folder
        for (child in tree[folder]) {
            dfs(child)
        }
        outCount[folder] = traverseCount
    }

    fun traverse() {
        dfs(1)
    }

    fun merge(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Pair<Int, Int> {
        val (leftMinVal, leftNodeCount) = p1
        val (rightMinVal, rightNodeCount) = p2
        return if (leftMinVal == rightMinVal) leftMinVal to (leftNodeCount + rightNodeCount)
        else if (leftMinVal > rightMinVal) p2
        else p1
    }

    fun initSeg(node: Int, s: Int, e: Int) {
        if (s == e) {
            seg[node] = 0 to 1
            return
        }
        val m = s + e shr 1
        initSeg(node shl 1, s, m)
        initSeg(node shl 1 or 1, m + 1, e)

        seg[node] = merge(seg[node shl 1], seg[node shl 1 or 1])
    }

    fun updateSeg(node: Int, s: Int, e: Int, l: Int, r: Int, v: Int) {
        updateLazy(node, s, e)
        if (e < l || r < s) return
        if (l <= s && e <= r) {
            lazy[node] = v
            updateLazy(node, s, e)
            return
        }
        val m = s + e shr 1
        updateSeg(node shl 1, s, m, l, r, v)
        updateSeg(node shl 1 or 1, m + 1, e, l, r, v)

        seg[node] = merge(seg[node shl 1], seg[node shl 1 or 1])
    }

    fun updateLazy(node: Int, s: Int, e: Int) {
        if (lazy[node] != 0) {
            if (s != e) {
                lazy[node shl 1] += lazy[node]
                lazy[node shl 1 or 1] += lazy[node]
            }

            val (minVal, nodeCount) = seg[node]
            seg[node] = (minVal + lazy[node]) to nodeCount

            lazy[node] = 0
        }
    }

    fun query(): Pair<Int, Int> {
        updateLazy(1, 1, n)
        return seg[1]
    }

    fun binarySearch(node: Int, s: Int, e: Int, tgt: Int): Int {
        updateLazy(node, s, e)
        if (s == e) return s
        val m = s + e shr 1
        updateLazy(node shl 1, s, m)
        val (lmv, lnc) = seg[node shl 1]

        return if (lmv > 0) binarySearch(node shl 1 or 1, m + 1, e, tgt)
        else if (lnc < tgt) binarySearch(node shl 1 or 1, m + 1, e, tgt - lnc)
        else binarySearch(node shl 1, s, m, tgt)
    }

    fun move(k: Int) {
        if (k > 0) {
            val (_, nodeCount) = query()
            cursor = min(cursor + k, nodeCount)
        } else {
            cursor = max(2, cursor + k)
        }
        curFolder = binarySearch(1, 1, n, cursor).let { ic -> inFolder[ic] }
    }

    fun toggle(folder: Int) {
        isFolded[folder] = !isFolded[folder]
        if (inCount[folder] == outCount[folder]) return
        val v = if (isFolded[folder]) 1 else -1
        updateSeg(1, 1, n, inCount[folder] + 1, outCount[folder], v)
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 폴더 정보 입력 처리
        readLine().split(" ").map { it.toInt() }.let { n = it[0]; q = it[1] }
        for (folder in 1..n) {
            tree[folder] = readLine().split(" ").map { it.toInt() }
                .let { it.slice(1..it.lastIndex) }
        }

        // 전처리
        traverse()
        initSeg(1, 1, n)
        for (folder in 2..n) { toggle(folder) }
        cursor = 2
        curFolder = tree[1][0]

        // 쿼리 입력 처리
        val sb = StringBuilder()
        repeat(q) {
            val inp = readLine()
            if (inp[0] == 't') {
                toggle(curFolder)
            } else {
                val k = inp.split(" ")[1].toInt()
                move(k)
                sb.append(curFolder).append('\n')
            }
        }

        // 출력
        print(sb)
    }
}


fun main() = with(P31402()) {
    solve()
}
