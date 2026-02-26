/**
 *
 * # 발견
 * 1. 문제의 설명에 따르면, 버스 교통 시스템은 크기가 N 인 트리 구조다.
 * 2. f(i)를 i번 정류장에 멈추는 버스의 수라고 하자.
 *    f(i)를 이루는 계산값은 아래와 같다.
 *    (1) i번 정류장에서 시작하거나 종료되는 버스 노선 = 2*(N-1)
 *    (2) i번 정류장을 경유하는 버스 노선 = (모든 j에 대해 ( g(j) * (N-1-g(j)) ) 의 총합) + (g(i) - 1) * (N - g(i))
 *        - j: i번 노드의 임의의 자식 노드
 *        - g(j): j번 노드를 루트로 하는 서브트리의 크기
 *
 * # 접근
 * - 트리에서의 DP
 * - 시간 O(N)
 * - 공간 O(N)
 * 1. 1번 노드를 루트로 간주하고 재귀 호출을 통해 f, g 함숫값을 계산 및 저장한다
 * 2. 계산식은 발견 2의 (1)과 (2)를 따른다
 * 
 */

class P12817 {

    val MAX_N = 1_000_000
    var N = 0
    val graph = Array(MAX_N+1) { mutableListOf<Int>() }
    val fArr = Array(MAX_N+1) { 0L }
    val gArr = Array(MAX_N+1) { 0L }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        N = readLine().toInt()
        repeat(N-1) {
            val (x, y) = readLine().split(" ").map(String::toInt)
            graph[x].add(y)
            graph[y].add(x)
        }

        // 풀이
        recur(1, 0)

        // 출력
        val sb = StringBuilder()
        (1..N).forEach { sb.append(fArr[it]).append('\n') }
        print(sb)
    }

    fun recur(cur: Int, prev: Int) {
        // 자식 노드 선별
        val childs = graph[cur].filter { it != prev }

        // 재귀 호출을 통해 먼저 f, g 계산
        for (next in childs) {
            recur(next, cur)
        }

        // 현재 노드의 f, g 계산
        var cf = 2L * (N - 1)
        var cg = 1L
        for (next in childs) {
            val ng = gArr[next]
            cf += ng * (N - 1 - ng)
            cg += gArr[next]
        }
        cf += (cg - 1) * (N - cg)

        // 현재 노드의 f, g 저장
        fArr[cur] = cf
        gArr[cur] = cg
    }
}

fun main() = with(P12817()) {
    solve()
}
