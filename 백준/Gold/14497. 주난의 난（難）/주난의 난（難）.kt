/**
 *
 * # 발견
 * 한 번의 점프는 한 겹의 친구를 쓰러뜨린다는 것, 예제의 케이스를 미루어 볼 때
 * 한 번의 점프로 *의 기준으로 경계면에 있는 친구는 모두 쓰러뜨릴 수 있고,
 * 그 다음 친구는 점프를 한 번 더 해야 쓰러뜨릴 수 있다.
 * 이를 naive하게 구현하면 시간 복잡도가 O(N^3)이 되는데, 300^3 은 아주 넉넉하다.
 * 한편, 단 한 번의 너비 우선 탐색으로 모든 지점을 단 한 번씩만 방문하도록 하면, 시간 복잡도가 O(N^2)이 된다.
 *
 * # 접근
 * - 구현, 너비 우선 탐색
 * - 시간 복잡도 O(N^3)
 * - 공간 복잡도 O(N^2)
 * 1. 경계면의 친구를 쓰러뜨리는 '점프' 함수를 구현한다.
 * 2. 범인이 쓰러질 때까지 '점프' 함수 시행을 반복한다.
 *
 */


class P14497 {

    class Node(
        val x: Int,
        val y: Int
    )

    val MAX_SIZE = 300
    val dx = arrayOf(0, 1, 0, -1)
    val dy = arrayOf(1, 0, -1, 0)
    val dq = ArrayDeque<Node>()
    val visited = Array(MAX_SIZE) { BooleanArray(MAX_SIZE) { false } }

    fun jump(x1: Int, y1: Int, n: Int, m: Int, grid: Array<CharArray>) {
        // 자료구조 초기화
        dq.clear()
        repeat(n) {
            visited[it].fill(false, 0, m)
        }

        // 너비 우선 탐색으로 쓰러뜨릴 친구들을 찾는다
        val targets = mutableListOf<Node>()
        dq.addLast(Node(x1, y1))
        visited[x1][y1] = true
        while (dq.isNotEmpty()) {
            val cur = dq.removeFirst()

            for (i in 0..3) {
                val nx = cur.x + dx[i]
                val ny = cur.y + dy[i]
                if (nx !in 0..<n || ny !in 0..<m) continue
                if (visited[nx][ny]) continue
                visited[nx][ny] = true
                val node = Node(nx, ny)
                if (grid[nx][ny] == '0') {
                    dq.addLast(node)
                } else {
                    targets.add(node)
                }
            }
        }

        // 친구들을 쓰러뜨린다
        targets.forEach { grid[it.x][it.y] = '0' }
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, m) = readLine().split(" ").map(String::toInt)
        val (x1, y1, x2, y2) = readLine().split(" ").map { it.toInt() - 1 }
        val grid = Array(n) { readLine().toCharArray() }

        // 풀이
        var answer = 0
        while (grid[x2][y2] == '#') {
            jump(x1, y1, n, m, grid)
            answer++
        }

        // 출력
        println(answer)
    }

}

fun main() = with(P14497()) {
    solve()
}