import kotlin.math.max

/**
 *
 * 완전 탐색으로 구현해도 O(N!) (N<=8) 로 시간 제한을 충족한다.
 *
 * # 접근
 * - 완전 탐색
 * - 시간 복잡도 O(N!)
 * - 공간 복잡도 O(N)
 *
 */


class P16987 {

    fun recur(holdingIndex: Int, n: Int, sArr: IntArray, wArr: IntArray): Int {
        if (holdingIndex == n) return sArr.count { hp -> hp <= 0 }
        if (sArr[holdingIndex] <= 0 || sArr.count { hp -> hp > 0 } == 1) return recur(holdingIndex + 1, n, sArr, wArr)
        var ret = 0
        for (i in 0 until n) {
            if (sArr[i] <= 0) continue
            if (holdingIndex == i) continue
            sArr[holdingIndex] -= wArr[i]
            sArr[i] -= wArr[holdingIndex]
            ret = max(ret, recur(holdingIndex + 1, n, sArr, wArr))
            sArr[holdingIndex] += wArr[i]
            sArr[i] += wArr[holdingIndex]
        }
        return ret
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()
        val eggs = Array(n) { readLine().split(" ").map { it.toInt() } }

        // 풀이
        val sArr = eggs.map { it[0] }.toIntArray()
        val wArr = eggs.map { it[1] }.toIntArray()
        val answer = recur(0, n, sArr, wArr)

        // 출력
        println(answer)
    }
}


fun main() = with(P16987()) {
    solve()
}