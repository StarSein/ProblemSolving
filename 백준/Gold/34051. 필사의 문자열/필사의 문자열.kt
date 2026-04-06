/**
 *
 * 1. s[i] < s[i+j] 인 (i, j) 가 단 하나라도 존재하면 뒤집는 수행을 해야 한다 (j > 0)
 * 2. 왼쪽부터 순회하면서 가장 먼저 만나는, 뒤에 더 큰 문자가 존재하는 인덱스를 l 이라고 하면,
 *    l 이상의 모든 인덱스 r에 대해
 *    s[l:r].split() 중 사전순 최댓값을 취하면 된다
 * 따라서 최종 시간 복잡도는 O(N^2) 이다.
 *
 */

class P34051 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()
        val s = readLine()

        // 풀이
        var l = -1
        outer@ for (i in 0..<n) {
            for (j in i + 1..<n) {
                if (s[i] < s[j]) {
                    l = i
                    break@outer
                }
            }
        }
        if (l == -1) {
            println(s)
            return
        }

        val maxSub = (l..<n).map {
            s.substring(l, it + 1).reversed() + if (it == n - 1) "" else s.substring(it + 1)
        }.max()

        val answer = s.substring(0, l) + maxSub
        println(answer)
    }
}


fun main() = with(P34051()) {
    solve()
}