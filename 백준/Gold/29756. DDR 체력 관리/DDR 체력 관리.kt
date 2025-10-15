import kotlin.math.max
import kotlin.math.min

/**
 *
 * # 접근
 * 1. DP - O(NT) (T = 100)
 * - cache[i][j]: i번째 구간을 끝내자마자 체력이 j이 되는 시점까지 얻을 수 있는 최대 점수
 * - maxOf( cache[N] ) 이 정답이 되도록 하자.
 * - 오버헤드를 줄이기 위해 Bottom-Up 방식으로 계산하자.
 *
 */

private class P29756 {

    var N = 0
    var K = 0
    val scores = IntArray(1001) { 0 }
    val healths = IntArray(1001) { 0 }
    val cache = Array(1001) { IntArray(101) { -1 } }
    // cache[i][j] 가 -1 이라면 i번째 구간을 끝내자마자 체력이 j가 되는 것은 불가능하다.

    var answer = 0

    fun readInput() = with(System.`in`.bufferedReader()) {
        readLine().split(" ").map(String::toInt).let { (n, k) ->
            N = n
            K = k
        }
        readLine().split(" ").map(String::toInt).toIntArray().copyInto(
            destination = scores,
            destinationOffset = 1
        )
        readLine().split(" ").map(String::toInt).toIntArray().copyInto(
            destination = healths,
            destinationOffset = 1
        )
    }

    fun solve() {
        cache[0][100] = 0

        repeat(N) { i ->
            repeat(101) { j ->
                cache[i][j].let { curScore ->
                    if (curScore != -1) {
                        // 다음 구간을 포기하는 경우
                        val j1 = min(100, j+K)
                        cache[i+1][j1] = max(cache[i+1][j1], curScore)

                        // 다음 구간을 플레이하는 경우
                        if (j >= healths[i+1]) {
                            val j2 = min(100, j-healths[i+1]+K)
                            cache[i+1][j2] = max(cache[i+1][j2], curScore + scores[i+1])
                        }
                    }
                }
            }
        }

        answer = cache[N].max()
    }

    fun writeOutput() {
        println(answer)
    }
}

fun main() = with(P29756()) {
    readInput()
    solve()
    writeOutput()
}