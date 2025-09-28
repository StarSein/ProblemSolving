/**
 *
 * # 접근
 * 1. 깊이 우선 탐색 - 시간 O(NM), 공간 O(NM)
 * 탐색 완료한 칸을 1로 표기하여 방문 처리한다.
 * 탐색 함수의 진입 시점에만 구역 개수를 센다.
 *
 * # 개선
 * 1. TLE: 재귀 함수는 100만 번 호출에 대해 유의미한 오버헤드가 있으므로, 스택으로 LIFO 구현
 *
 */

private class P27211 {

    var N = 0
    var M = 0
    var grid: Array<IntArray> = emptyArray()
    val dr = intArrayOf(0, 1, 0, -1)
    val dc = intArrayOf(1, 0, -1, 0)
    var answer = 0

    fun readInput() = with(System.`in`.bufferedReader()) {
        readLine().split(" ").map(String::toInt).let { (n, m) ->
            N = n
            M = m
        }
        grid = Array(N) { readLine().split(" ").map(String::toInt).toIntArray() }
    }

    fun solve() {
        repeat(N) { r ->
            repeat(M) { c ->
                if (grid[r][c] == 0) {
                    grid[r][c] = 1
                    answer++
                    dfs(r, c)
                }
            }
        }
    }

    fun dfs(r: Int, c: Int) {
        val dq = ArrayDeque<Pair<Int, Int>>()
        dq.addLast(r to c)
        while (dq.isNotEmpty()) {
            val (cr, cc) = dq.removeLast()
            repeat(4) {
                val nr = (cr + dr[it] + N) % N
                val nc = (cc + dc[it] + M) % M
                if (grid[nr][nc] == 0) {
                    grid[nr][nc] = 1
                    dq.addLast(nr to nc)
                }
            }
        }
    }

    fun printAnswer() {
        print(answer)
    }
}

fun main() = with(P27211()) {
    readInput()
    solve()
    printAnswer()
}