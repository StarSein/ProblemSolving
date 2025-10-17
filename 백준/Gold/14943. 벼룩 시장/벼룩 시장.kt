import kotlin.math.abs

/**
 *
 * # 접근
 * 1. 그리디, 스위핑 - O(N)
 * - 맨 왼쪽부터 오른쪽으로 구매 용의와 판매 용의를 처리 가능할 때마다 처리하면 최적이다.
 * - 배달 비용은 한 칸씩 이동할 때마다 벼룩의 (공급량 - 수요량)의 절댓값만큼 증가시키면 된다.
 *
 */

private class P14943 {

    var N = 0
    var L = longArrayOf()
    var answer = 0L

    fun readInput() = with(System.`in`.bufferedReader()) {
        N = readLine().toInt()
        L = readLine().split(" ").map(String::toLong).toLongArray()
    }

    fun solve() {
        var diff = 0L
        L.forEach { l ->
            answer += abs(diff)
            diff += l
        }
    }

    fun writeOutput() {
        println(answer)
    }
}

fun main() = with(P14943()) {
    readInput()
    solve()
    writeOutput()
}