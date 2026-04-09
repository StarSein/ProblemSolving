/**
 *
 * 초기 상태에서
 * R r 쿼리 -> r * n + n * (n + 1) / 2
 *
 * 이 상태에서
 * C c 쿼리 -> c * n + n * (n + 1) / 2 - (c + r)
 *
 * R 쿼리는 이후 C 쿼리의 결과값에만 영향을 주고,
 * C 쿼리도 이후 R 쿼리의 결과값에만 영향을 준다.
 *
 * R 쿼리로 인해 0으로 바뀐 r행은 모든 C 쿼리에 일괄 적용된다
 * C 쿼리의 영향 또한 모든 R 쿼리에 일괄 적용된다
 *
 * R 쿼리와 C 쿼리와 연관된 변수 2개를 관리하면 된다
 *
 * 시간 복잡도 O(Q)
 *
 */


class P11735 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, q) = readLine().split(" ").map { it.toInt() }

        // 쿼리 입력 및 처리
        val isRowEmpty = BooleanArray(n + 1) { false }
        val isColEmpty = BooleanArray(n + 1) { false }
        var rCnt = 0
        var cCnt = 0
        var rSum = 0L
        var cSum = 0L
        val initSum = n.toLong() * (n + 1) / 2

        val sb = StringBuilder()
        var res = -1L
        repeat(q) {
            val (type, num) = readLine().split(" ")
            if (type == "R") {
                val r = num.toInt()
                if (isRowEmpty[r]) {
                    res = 0
                } else {
                    isRowEmpty[r] = true
                    res = r.toLong() * (n - cCnt) + initSum - cSum
                    rCnt++
                    rSum += r
                }
            } else {
                val c = num.toInt()
                if (isColEmpty[c]) {
                    res = 0
                } else {
                    isColEmpty[c] = true
                    res = c.toLong() * (n - rCnt) + initSum - rSum
                    cCnt++
                    cSum += c
                }
            }
            sb.append(res).append('\n')
        }

        // 결과 출력
        print(sb)
    }
}


fun main() = with(P11735()) {
    solve()
}