import kotlin.math.max

/**
 *
 * # 접근
 * 1. DP - O(N^2)
 * - 위쪽과 오른쪽 이동만 가능하기 때문에, 재귀적으로 접근해도 사이클이 발생하지 않는다
 *   따라서 좌표 평면 위의 모든 점에서 왼쪽과 오른쪽 이동 사이의 최적 선택 문제로 접근하고,
 *   계산된 값을 메모이제이션하고, 필요할 때마다 꺼내쓰면 된다.
 *   cache[r][c] = max(cache[r+1][c], cache[r][c+1])
 * - 또한, 위쪽과 오른쪽 이동만 가능하기 때문에,
 *   (x, y) 위의 어떤 사탕 바구니에 도달했을 때
 *   그 경로에 관계 없이 항상 일정한 양의 사탕을 먹게 되며,
 *   그 값은 max(M-x-y, 0)와 같다
 *
 */

private class P14585 {

    var N = 0
    var M = 0
    var grid = Array(301) { BooleanArray(301) }
    // grid[r][c]: (r, c)에 사탕 바구니가 있는지 여부
    var cache = Array(301) { IntArray(301) { -1 } }
    // cache[r][c]: (r, c)에 도달한 이후 최적 선택을 통해 추가로 먹을 수 있는 사탕의 개수
    var answer = 0

    fun readInput() = with(System.`in`.bufferedReader()) {
        readLine().split(" ").map(String::toInt).let { (n, m) ->
            N = n
            M = m
        }
        repeat(N) {
            readLine().split(" ").map(String::toInt).let { (x, y) ->
                grid[x][y] = true
            }
        }
    }

    fun recur(r: Int, c: Int): Int {
        if (r > 300 || c > 300) return 0
        cache[r][c].let {
            if (it != -1) return it
        }
        val ret = max(
            recur(r+1, c), 
            recur(r, c+1)
        ) + if (grid[r][c]) max(M-r-c, 0) else 0
        return ret.also { cache[r][c] = it }
    }

    fun solve() {
        answer = recur(0, 0)
    }

    fun writeOutput() {
        print(answer)
    }
}

fun main() = with(P14585()) {
    readInput()
    solve()
    writeOutput()
}