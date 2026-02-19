import kotlin.math.min

/**
 *
 * # 발견
 *      [0 2 0 1] [0 0] [2 1 0 2]
 * l l    1 + 1     0     0 + 1
 * l r    1 + 1     0     1 + 1
 * r l    1 + 1     0     1 + 1
 * r r    1 + 0     0     1 + 1
 * l x
 * r x
 * 분할 정복은 안 되겠다.
 * 건물의 높이가 높을수록 더 먼 건물까지 영향을 미치고, 그때는 구간을 어떻게 분할하느냐에 따라 계산이 달라진다.
 *
 *        1 3 6 7 9
 *        2 1 2 1 2
 *        -------->
 * l      1 2 2 2 2
 * r      1 1 2 2 3
 *
 * 이렇게 왼쪽에서 오른쪽 방향으로 훑으면서,
 * 현재 도미노가 왼쪽으로 쓰러지는 건 그만큼 왼쪽으로 이동하여 도착한 곳의 최적해로부터 전이하고,
 * 오른쪽으로 쓰러지는 건 그만큼 오른쪽으로 이동하는 동안 현재의 최적해를 전이해 놓는 식으로 구현할까?
 * 이렇게 하면 O(N^2)으로 시간 제한에는 문제가 없다.
 *
 * # 풀이
 * 1. 도미노를 X 좌표를 기준으로 정렬한다
 * 2. 1번째 도미노부터 순회한다
 *  1) left[i]를 i번째 도미노가 왼쪽으로 쓰러질 때의 최적해, right[i]를 i번째 도미노가 오른쪽으로 쓰러질 때의 최적해라고 하자.
 *     이때 1~i번째 도미노 모두가 쓰러져야 한다
 *  2) i번째 도미노가 왼쪽으로 쓰러져서 최대 j번째 도미노까지 쓰러뜨린다면 left[i] = min(left[j-1], right[j-1])
 *  3) i번째 도미노가 오른쪽으로 쓰러져서 최대 j번째 도미노까지 쓰러뜨린다면 right[k] = min(right[k], right[i])
 *     이때 right[i] = min(right[i], left[i-1], right[i-1])
 * 3. 정답은 min(left[N], right[N]) 의 값이다
 * 
 */


class P14586 {

    class Domino(
        val x: Long,
        val h: Long
    )

    var N = 0
    var dominos = emptyList<Domino>()
    val INF = 301
    var answer = INF

    fun input() = with(System.`in`.bufferedReader()) {
        N = readLine().toInt()
        dominos = List(N) {
            readLine().split(" ").map(String::toLong).let {
                Domino(it[0], it[1])
            }
        }
    }

    fun solve() {
        dominos = dominos.sortedBy { it.x }

        val left = Array(N) { INF }
        val right = Array(N) { INF }
        for (i in 0..<N) {
            // 왼쪽으로 i번째 도미노가 쓰러지는 일 처리
            var j = i - 1
            var strongDomino = dominos[i]
            while (j >= 0 && strongDomino.x - dominos[j].x <= strongDomino.h) {
                if (dominos[j].h > strongDomino.h - (strongDomino.x - dominos[j].x)) {
                    strongDomino = dominos[j]
                }
                j--
            }
            if (j >= 0) {
                left[i] = min(left[j] + 1, right[j] + 1)
            } else {
                left[i] = 1
            }

            // 오른쪽으로 i번째 도미노가 쓰러지는 일 처리
            if (i == 0) {
                right[i] = 1
            } else {
                right[i] = min(right[i], left[i-1] + 1)
                right[i] = min(right[i], right[i-1] + 1)
            }

            j = i + 1
            strongDomino = dominos[i]
            while (j < N && dominos[j].x - strongDomino.x <= strongDomino.h) {
                if (dominos[j].h > strongDomino.h - (dominos[j].x - strongDomino.x)) {
                    strongDomino = dominos[j]
                }
                right[j] = min(right[j], right[i])
                j++
            }
        }

        answer = min(left[N-1], right[N-1])
    }

    fun output() {
        println(answer)
    }
}

fun main() = with(P14586()) {
    input()
    solve()
    output()
}