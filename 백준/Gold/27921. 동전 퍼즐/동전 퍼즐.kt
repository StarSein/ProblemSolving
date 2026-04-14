import kotlin.math.min

/**
 *
 * 만들어야 하는 동전 배치에서 기존 동전 배치와 비교하기 위한 시작점(왼쪽 위의 꼭짓점)을 설정한다 - O(HW) * O(HW)
 * 두 배치를 비교 - O(HW)
 * 해서 동일한 위치의 'O'의 개수의 최댓값을 M이라고 할 때
 * 전체 동전의 개수에서 M을 뺀 값이 정답이 된다
 *
 * 전체 시간 복잡도 O(N^6)
 *
 */


class P27921 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (h1, w1) = readLine().split(" ").map { it.toInt() }
        val grid1 = Array(h1) { readLine().toCharArray() }
        val (h2, w2) = readLine().split(" ").map { it.toInt() }
        val grid2 = Array(h2) { readLine().toCharArray() }

        // 풀이
        var maxMatchCount = 0
        val mh = min(h1, h2)
        val mw = min(w1, w2)
        for (sr1 in 0..<h1) {
            for (sc1 in 0..<w1) {
                for (sr2 in 0..<h2) {
                    for (sc2 in 0..<w2) {
                        var matchCount = 0
                        for (r in 0..<mh) {
                            if (sr1 + r >= h1 || sr2 + r >= h2) continue
                            for (c in 0..<mw) {
                                if (sc1 + c >= w1 || sc2 + c >= w2) continue
                                if (grid1[sr1+r][sc1+c] == 'O' && grid2[sr2+r][sc2+c] == 'O') {
                                    matchCount++
                                }
                            }
                        }
                        if (matchCount > maxMatchCount) {
                            maxMatchCount = matchCount
                        }
                    }
                }
            }
        }
        val totalCoinCount = grid1.sumOf { row -> row.count { it == 'O' } }
        val answer = totalCoinCount - maxMatchCount

        // 출력
        println(answer)
    }

}


fun main() = P27921().solve()