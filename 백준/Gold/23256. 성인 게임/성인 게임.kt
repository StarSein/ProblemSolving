/**
 *
 * # 발견
 * A_i 를 정사각형 i개로 만들 수 있는 칼날의 개수라고 하자.
 * B_i 를 맨 오른쪽 모서리가 비어있는 칼날의 개수라고 하자.
 * N=1) A_1 = 7, B_1 = 3
 * N=2) A_2 = 3 * A_1 + 4 * B_1 = 21 + 12 = 33
 *      B_2 = A_1 + 2 * B_1 = 7 + 6 = 13
 * N=i) A_i = 3 * A_{i-1} + 4 * B_{i-1}
 *      B_i = A_{i-1} + 2 * B_{i-1}
 * 중복으로 카운트 되는 경우는 없으므로, 유효한 점화식이다.
 * # 풀이
 * DP
 * 시간 O(N+T), 공간 O(N)
 * 1. 점화식을 그대로 구현하고 저장해 놓는다.
 * 2. 매 테스트케이스마다 저장된 A[N] 값을 출력한다.
 *    - O(NT) 로 풀이하면 최악의 경우 시간 초과가 난다.
 */


class P23256 {

    var T = 0
    var nArr = emptyArray<Int>()
    val MOD = 1_000_000_007L
    val MAX_N = 1_000_000
    val A = Array(MAX_N+1) { 1L }
    val B = Array(MAX_N+1) { 1L }
    val sb = StringBuilder()

    fun input() = with(System.`in`.bufferedReader()) {
        T = readLine().toInt()
        nArr = Array(T) { readLine().toInt() }
    }

    fun solve() {
        for (i in 1..MAX_N) {
            A[i] = (3 * A[i-1] + 4 * B[i-1]) % MOD
            B[i] = (A[i-1] + 2 * B[i-1]) % MOD
        }

        nArr.forEach { N -> sb.append(A[N]).append('\n') }
    }

    fun output() {
        print(sb)
    }
}

fun main() = with(P23256()) {
    input()
    solve()
    output()
}