/**
 *
 *
 * { 0 2 5 }
 *
 * 1 0 1 0 1 1 0 1 0
 * 0 1 1 2 2 2 3 3 4 - 초기 소등한 방의 개수 누적합 minCount
 * 1 1 2 2 3 4 4 5 5 - 신규 소등 가능한 방의 개수 누적합
 * 1 2 3 4 5 6 7 8 9   둘의 합 maxCount
 *
 * 인덱스 i를 0부터 N-1까지 스위핑하면서 누적해 온 m값과 M값에 대해
 * [minCount, maxCount]에 집합 A의 원소 x가 존재하면 i번째 방은 소등 가능하다
 *  i번째 방의 소등은 구간 [0, i-1]에는 영향을 주지 않기 때문에
 *  소등할 수 있는 게 발생할 때마다 오른쪽에서부터 소등한다고 하면,
 *  위 조건을 만족하는 모든 인덱스 i에 대해 구간 [0, i-1]의 소등 개수를 어떤 x로 맞출 수 있고,
 *  따라서 소등이 가능하다.
 *
 */



class P33070 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, k) = readLine().split(" ").map { it.toInt() }
        val aSet = readLine().split(" ").map { it.toInt() }.sorted()
        val isOff = readLine().split(" ").map { it == "0" }.toBooleanArray()

        // 풀이
        val dq = ArrayDeque(aSet)

        var minCount = 0
        var maxCount = 0

        isOff.forEach { off ->
            if (off) {
                minCount++
                maxCount++
            } else {
                while (dq.isNotEmpty() && dq.first() < minCount) {
                    dq.removeFirst()
                }
                if (dq.isNotEmpty() && dq.first() in minCount..maxCount) {
                    maxCount++
                }
            }
        }
        val answer = n - maxCount

        // 출력
        println(answer)

    }
}


fun main() = with(P33070()) {
    solve()
}