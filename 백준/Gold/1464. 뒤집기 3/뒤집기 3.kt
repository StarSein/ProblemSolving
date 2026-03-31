/**
 *
 * 완전 탐색으로, 뒤집을 i의 집합을 구하는 모든 경우의 수는 최대 2^50 이다.
 * 어떻게 경우의 수를 줄일까?
 * 구간 [1:i]를 뒤집을지 여부를 결정할 때, 구간 [1:i-1]에 사전순으로 가장 앞서는 것과 가장 뒤에 있는 것 둘만 있으면 된다.
 * 이렇게 시간 복잡도는 O(2^N)에서 O(N^2)으로 최적화된다.
 *
 */

class P1464 {


    fun minRev(a: String, b: String) = if (a.reversed() < b.reversed()) a else b
    fun min(a: String, b: String) = if (a < b) a else b

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val inp = readLine()

        // 풀이
        var first = inp.substring(0, 1)
        var last = inp.substring(0, 1)
        for (i in 1..<inp.length) {
            val fc = first + inp.substring(i, i+1)
            val lc = last + inp.substring(i, i+1)
            first = min(fc, lc.reversed())
            last = minRev(lc, fc.reversed())
        }

        // 출력
        println(first)
    }

}


fun main() = with(P1464()) {
    solve()
}