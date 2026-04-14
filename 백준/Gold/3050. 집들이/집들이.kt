import kotlin.math.max

/**
 *
 * O(N^3) 풀이
 * 1. 테이블의 행 길이를 1부터 R까지 시도해본다 - O(N)
 * 2. R*C 전체를 순회하며 시도해본다 - O(N^2)
 * 3. 단, 이때 (i, j)에서 아랫 방향으로 연속한 '.' 구간의 최대 길이는 저장되어 있어야 한다
 *
 */


class P3050 {
    
    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (r, c) = readLine().split(" ").map { it.toInt() }
        val grid = Array(r) { readLine().toCharArray() }

        // 풀이
        val maxRs = Array(r + 1) { IntArray(c + 1) { 0 } }
        for (i in grid.lastIndex downTo 0) {
            for (j in 0..<c) {
                if (grid[i][j] == '.') {
                    maxRs[i][j] = 1 + maxRs[i+1][j]
                }
            }
        }

        var answer = 0
        for (h in 1..grid.size) {
            val li = grid.lastIndex - h + 1
            for (i in 0..li) {
                var w = 0
                for (j in 0..c) {
                    if (maxRs[i][j] >= h) {
                        w++
                    } else if (w != 0){
                        answer = max(answer, 2 * (h + w))
                        w = 0
                    }
                }
            }
        }
        answer--

        // 출력
        println(answer)
    }

}


fun main() = P3050().solve()