/**
 *
 * 1. 양의 정수 배열과 음의 정수 배열, 0 배열로 나눈다
 * 2. 양의 정수 배열에서는 가장 큰 두 수를 한 쌍으로 제거한다. 이를 반복한다
 *    마지막에 하나의 수만 남으면 하나만 제거한다
 * 3. 음의 정수 배열에서는 가장 절댓값이 큰 두 수를 한 쌍으로 제거한다. 이를 반복한다
 *    마지막에 하나의 수만 남으면 0 배열이 비어있지 않은 경우 0으로 처리한다
 *    비어 있는 경우 하나만 제거한다
 *
 */


class P2036 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()
        val pos = mutableListOf<Long>()
        val neg = mutableListOf<Long>()
        var oneCount = 0
        var zeroCount = 0
        repeat(n) {
            val num = readLine().toInt()
            if (num > 1) pos.add(num.toLong())
            else if (num < 0) neg.add(num.toLong())
            else if (num == 1) oneCount++
            else zeroCount++
        }

        // 풀이
        pos.sortDescending()
        neg.sort()

        var answer = oneCount.toLong()

        var pi = 0
        while (pi < pos.lastIndex) {
            answer += pos[pi] * pos[pi+1]
            pi += 2
        }
        if (pi == pos.lastIndex) {
            answer += pos[pi]
        }
        var ni = 0
        while (ni < neg.lastIndex) {
            answer += neg[ni] * neg[ni+1]
            ni += 2
        }
        if (ni == neg.lastIndex) {
            answer += if (zeroCount == 0) neg[ni] else 0L
        }

        // 출력
        println(answer)
    }

}


fun main() = with(P2036()) {
    solve()
}