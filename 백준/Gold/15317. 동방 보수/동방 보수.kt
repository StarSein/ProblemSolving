import kotlin.math.max
import kotlin.math.min

class P15317 {

    fun isOk(num: Int, x: Int, costs: IntArray, budgets: IntArray): Boolean {
        var i = num - 1
        var j = budgets.lastIndex
        var sum = 0
        while (i >= 0 && sum <= x) {
            sum += max(0, costs[i] - budgets[j])
            i--
            j--
        }
        return sum <= x
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, m, x) = readLine().split(" ").map(String::toInt)
        val costs = readLine().split(" ").map(String::toInt)
            .toIntArray().sortedArray()
        val budgets = readLine().split(" ").map(String::toInt)
            .toIntArray().sortedArray()

        // 풀이
        var s = 1
        var e = min(n, m)
        var answer = -1
        while (s <= e) {
            val mid = (s + e) / 2
            if (isOk(mid, x, costs, budgets)) {
                answer = mid
                s = mid + 1
            } else {
                e = mid - 1
            }
        }

        // 출력
        println(answer)
    }
}


fun main() = with(P15317()) {
    solve()
}