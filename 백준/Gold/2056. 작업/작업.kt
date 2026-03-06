import java.util.PriorityQueue

/**
 *
 * # 접근
 * - 위상정렬
 * - 시간 복잡도 O(NlogN)
 * - 공간 복잡도 O(N)
 * - 자료 구조
 *  1) postWorks[id]: Array of MutabaleList<Int>
 *  2) preCounts[id]: Array of Int
 *  3) times[id]: Array of Int
 *  4) pq: PriorityQueue<Pair<Int, Int>>
 */


class P2056 {

    class Work(
        val id: Int,
        val endTime: Int
    )

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()
        val postWorks = Array(n + 1) { mutableListOf<Int>() }
        val preCounts = IntArray(n + 1) { 0 }
        val times = IntArray(n + 1) { 0 }
        (1..n).forEach { id ->
            readLine().split(" ").map(String::toInt).let {
                times[id] = it[0]
                it.slice(2..(it.lastIndex)).let { pwArr ->
                    postWorks[id].addAll(pwArr)
                    pwArr.forEach { pw -> preCounts[pw]++ }
                }
            }
        }

        // 풀이
        var answer = 0
        val pq = PriorityQueue<Work> { o1, o2 -> o1.endTime - o2.endTime }
        pq.addAll((1..n).filter { preCounts[it] == 0 }.map { Work(it, times[it]) })
        while (pq.isNotEmpty()) {
            val cur = pq.poll()
            answer = cur.endTime
            postWorks[cur.id].forEach { pw ->
                if (--preCounts[pw] == 0) {
                    pq.offer(Work(pw, cur.endTime + times[pw]))
                }
            }
        }

        // 출력
        println(answer)
    }
}

fun main() = with(P2056()) {
    solve()
}