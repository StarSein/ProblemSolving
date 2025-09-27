import kotlin.math.max

/**
 *
 * # 접근
 * 1. DP - 시간 O(N^2), 공간 O(N^2)
 * cache[X][Y]: (0,0)부터 (X,Y)에 도달하는 경로에서 얻을 수 있는 아름다움의 최댓값
 * 도달할 수 없는 경우 -1이라고 하자.
 * cache[X][Y] = max(cache[X-1][Y-1], cache[X-1][Y+1]) 이 성립한다
 * 위 계산식을 통해 재귀적으로 cache[2N][0]을 구할 수 있다.
 * 단, 재귀함수의 중복 호출에 따른 오버헤드를 개선하기 위해 2차원 배열에 계산값을 메모이제이션 하자.
 *
 */

private class P31671 {
    var N = 0
    var M = 0
    lateinit var isTeacher: Array<BooleanArray>
    lateinit var cache: Array<IntArray>
    var answer = -1

    fun readInput() = with(System.`in`.bufferedReader()) {
        readLine().split(" ").map(String::toInt).let { (n, m) ->
            N = n
            M = m
        }
        isTeacher = Array(2*N+1) { BooleanArray(N+1) }
        repeat(M) {
            val (x, y) = readLine().split(" ").map(String::toInt)
            isTeacher[x][y] = true
        }
    }

    fun solve() {
        cache = Array(2*N+1) { IntArray(N+1) { -2 } }
        cache[0][0] = 0
        answer = recur(2*N, 0)
    }

    fun recur(x: Int, y: Int): Int {
        // 삼각형 안에서만 이동 가능하다
        if (x < 0 || y < 0 ||
            x <= N && y > x ||
            x > N && y > 2*N - x) return -1
        // 선생님을 피해 가야 한다
        if (isTeacher[x][y]) return -1
        // 계산된 값이 있으면 사용한다
        if (cache[x][y] != -2) return cache[x][y]
        // 계산된 값이 없으면 재귀 호출을 통해 계산한다
        val prev = max(
            recur(x-1, y-1),
            recur(x-1, y+1),
        )
        val ret = if (prev == -1) -1
        else max(prev, y)
        return ret.also { cache[x][y] = it }
    }

    fun writeAnswer() {
        print(answer)
    }
}

fun main() = with(P31671()) {
    readInput()
    solve()
    writeAnswer()
}