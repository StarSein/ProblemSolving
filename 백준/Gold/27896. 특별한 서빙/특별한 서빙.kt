import java.util.PriorityQueue


class P27896 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, m) = readLine().split(" ").map { it.toLong() }
        val xArr = readLine().split(" ").map { it.toInt() }

        // 풀이
        var answer = 0
        var score = 0L
        val pq = PriorityQueue<Int> { o1, o2 -> o2 - o1 }
        xArr.forEach { x ->
            score += x
            pq.add(x)

            if (score >= m) {
                answer++
                score -= 2 * pq.remove()
            }
        }

        // 출력
        println(answer)
    }
}


fun main() = P27896().solve()