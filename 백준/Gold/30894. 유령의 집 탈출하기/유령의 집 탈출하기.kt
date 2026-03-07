/**
 *
 * # 발견
 * 1. 석준이가 발각되지 않는 공간 구성은 4초 주기로 반복된다.
 *    따라서 시간을 4로 나눈 나머지 값에 대한 각각의 2차원 배열을 만들어 놓자.
 * 2. 이런 관점에서 현재 상태에서 유의미한, 구별되는 정보는 (행 좌표, 열 좌표, 시간을 4로 나눈 나머지)가 된다.
 *    따라서 이에 대해 중복 방문을 없애면서 너비 우선 탐색을 하면 최단 경로를 구할 수 있다.
 *
 * # 접근
 * - 너비 우선 탐색
 * - 시간 복잡도 O(NM)
 * - 공간 복잡도 O(NM)
 * 1. 시간 t에 대해 석준이가 갈 수 있는 2차원 공간을 계산한다. (t = 0,1,2,3)
 * 2. 계산한 2차원 배열을 이용하여 너비 우선 탐색을 통해 최단 경로를 계산한다. 
 *
 */


class P30894 {
    
    fun solve() = with(System.`in`.bufferedReader()) mainFunc@ {
        // 입력
        val (n, m) = readLine().split(" ").map(String::toInt)
        val (sx, sy, ex, ey) = readLine().split(" ").map { it.toInt() - 1 }
        val grid = Array(n) { readLine().toCharArray() }

        // 풀이
        val reachable = Array(4) {
            Array(n) { x ->
                BooleanArray(m) { y ->
                    grid[x][y] == '.'
                }
            }
        }
        val ghosts = mutableListOf<Pair<Int, Int>>()
        repeat(n) { x ->
            repeat(m) { y ->
                if (grid[x][y] in '0'..'3') {
                    ghosts.add(x to y)
                }
            }
        }
        val dx = arrayOf(0, 1, 0, -1)
        val dy = arrayOf(1, 0, -1, 0)
        repeat(4) { t ->
            val rcb = reachable[t]
            ghosts.forEach { (gx, gy) ->
                val i = (grid[gx][gy] - '0' + t) % 4
                var nx = gx + dx[i]
                var ny = gy + dy[i]
                while (nx in 0..<n && ny in 0..<m && grid[nx][ny] == '.') {
                    rcb[nx][ny] = false
                    nx += dx[i]
                    ny += dy[i]
                }
            }
        }

        val dq = ArrayDeque<Pair<Int, Int>>()
        val visited = Array(4) { Array(n) { BooleanArray(m) { false } } }
        dq.addLast(sx to sy)
        visited[0][sx][sy] = true
        var answer = 0
        var t = 0
        while (dq.isNotEmpty()) {
            answer++
            t = (t + 1) % 4
            repeat(dq.size) {
                val (cx, cy) = dq.removeFirst()

                if (reachable[t][cx][cy] && !visited[t][cx][cy]) {
                    dq.addLast(cx to cy)
                    visited[t][cx][cy] = true
                }

                repeat(4) {
                    val nx = cx + dx[it]
                    val ny = cy + dy[it]
                    if (nx in 0..<n && ny in 0..<m && reachable[t][nx][ny] && !visited[t][nx][ny]) {
                        if (nx == ex && ny == ey) {
                            println(answer) // 정답 출력
                            return@mainFunc
                        }
                        dq.addLast(nx to ny)
                        visited[t][nx][ny] = true
                    }
                }
            }
        }

        // 탈출 실패한 경우
        println("GG")
    }
    
}

fun main() = with(P30894()) {
    solve()
}