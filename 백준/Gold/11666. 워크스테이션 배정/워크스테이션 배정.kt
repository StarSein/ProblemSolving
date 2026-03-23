import java.util.PriorityQueue

/**
 *
 * 연구원들이 도착하자마자 워크스테이션을 배정받아야 하므로, 선택의 여지는 오직 둘 중 하나다.
 * 1. 잠금이 풀린 빈 워크스테이션 배정 (기왕이면 가장 먼저 잠길 예정인 것으로)
 * 2. 잠겨있는 워크스테이션 배정 (처음부터 잠겨있거나, 시간이 흘러 자동으로 잠겼거나)
 *
 * # 접근
 * - 그리디
 * - 시간 복잡도 O(NlogN)
 * - 공간 복잡도 O(N)
 *
 */


class P11666 {


    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, m) = readLine().split(" ").map { it.toInt() }
        val schedule = List(n) { readLine().split(" ").map { it.toInt() } }
            .map { it[0] to it[1] }
            .sortedBy { it.first }

        // 풀이
        var answer = 0
        val occupiedPQ = PriorityQueue<Int>()
        val unlockedPQ = PriorityQueue<Int>()
        schedule.forEach { (a, s) ->
            while (occupiedPQ.isNotEmpty() && occupiedPQ.first() <= a) {
                unlockedPQ.add(occupiedPQ.remove() + m)
            }
            while (unlockedPQ.isNotEmpty() && unlockedPQ.first() < a) {
                unlockedPQ.remove()
            }
            if (unlockedPQ.isNotEmpty()) {
                unlockedPQ.remove()
                answer++
            }
            occupiedPQ.add(a + s)
        }

        // 출력
        println(answer)
    }
}


fun main() = with(P11666()) {
    solve()
}