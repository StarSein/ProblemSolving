import kotlin.math.max

/**
 *
 * # 발견
 * 같은 장르 안에서는 무조건 비싼 것부터 팔면 된다.
 * 각 장르를 몇 개씩 팔 것인지를 결정하는 문제다.
 * 1번부터 10번 장르까지 순차적으로 판매 부수를 결정할 때, i번째에서 고려 대상이 되는 상태 정보는 (i-1번째까지 총 판매 부수)뿐이다.
 * 각 상태 정보에 대해 매입 가격의 최댓값을 유지하면 된다.
 *
 * # 접근
 * - DP
 * - 시간 복잡도 O(K^2)
 * - 공간 복잡도 O(K)
 *
 */


class P5550 {

    fun solve() = with(System.`in`.bufferedReader()) {
        val gCount = 10
        // 입력
        val (n, k) = readLine().split(" ").map { it.toInt() }
        val books = Array(gCount) { mutableListOf<Int>() }
        repeat(n) {
            val (c, g) = readLine().split(" ").map { it.toInt() }
            books[g-1].add(c)
        }

        // 풀이
        books.forEach { book -> book.sortDescending() }

        val dp = Array(gCount + 1) { Array(k + 1) { 0 } }
        books.forEachIndexed { i, book ->
            val curDp = dp[i]
            dp[i+1] = dp[i].copyOf() // 특정 장르 번호에 책이 단 한 권도 없는 경우를 대비함
            val nextDp = dp[i+1]

            var prefixSum = 0
            var count = 0
            book.forEach { price ->
                count++
                prefixSum += price
                val bonus = (count - 1) * count
                val revenue = prefixSum + bonus

                for (j in 0..k-count) {
                    nextDp[j+count] = max(nextDp[j+count], curDp[j] + revenue)
                }
            }
        }

        // 출력
        val answer = dp[gCount][k]
        println(answer)
    }
}

fun main() = with(P5550()) {
    solve()
}