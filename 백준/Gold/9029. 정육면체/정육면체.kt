import kotlin.math.min

/**
 *
 * dp[i][j][k]: 세 변의 길이가 i <= j <= k 인 직사각형을 정육면체들로 분할하기 위한
 *              최소한의 절단 횟수, 그리고 그때 정육면체의 개수
 *              절단 횟수를 최소로 한다는 말은 정육면체의 개수를 최소로 한다는 말과 동치이다
 *              즉, 정육면체 개수의 최솟값
 *              
 * 1) (i, j, k) 의 개수 = H(200, 3) = C(202, 3) = 1_353_400
 * 2) 각 상태마다 써는 방법의 수 = (i / 2) + (j / 2) + (k / 2)
 * 3) 총 루프의 반복 횟수 = 203_010_000
 *
 * 전체 시간 복잡도 O(N^3)
 *
 */


class P9029 {

    val sz = 200
    val inf = 100_000_000
    val dp = Array(sz + 1) { Array(sz + 1) { IntArray(sz + 1) { inf } } }

    fun calc(a: Int, b: Int, c: Int, lRange: IntRange): Int {
        var ret = inf
        for (l in lRange) {
            val res1 = if (b <= l) dp[a][b][l]
            else if (a <= l) dp[a][l][b]
            else dp[l][a][b]

            val r = c - l
            val res2 = if (b <= r) dp[a][b][r]
            else if (a <= r) dp[a][r][b]
            else dp[r][a][b]

            ret = min(ret, res1 + res2)
        }
        return ret
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val t = readLine().toInt()

        // 전처리
        for (k in 1..sz) {
            for (j in 1..k) {
                for (i in 1..j) {
                    if (i == j && j == k) {
                        dp[i][j][k] = 1
                        continue
                    }
                    dp[i][j][k] = min(dp[i][j][k], calc(i, j, k, 1..k/2))
                    dp[i][j][k] = min(dp[i][j][k], calc(i, k, j, 1..j/2))
                    dp[i][j][k] = min(dp[i][j][k], calc(j, k, i, 1..i/2))
                }
            }
        }

        // 테케 입력 및 풀이
        val sb = StringBuilder()
        repeat(t) {
            val (w, l, h) = readLine().split(" ").map { it.toInt() }
                .sorted()
            val answer = dp[w][l][h]
            sb.append(answer).append('\n')
        }

        // 전체 출력
        print(sb)
    }
}


fun main() = with(P9029()) {
    solve()
}