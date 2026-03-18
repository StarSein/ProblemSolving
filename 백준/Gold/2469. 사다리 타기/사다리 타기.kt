/**
 *
 * 전체 완전 탐색의 경우의 수는 2^25 = 약 32,000,000개 미만
 * 여기에 k = 25를 곱한다고 하면 완전 탐색은 시간 초과가 예상된다.
 *
 * 하나의 column마다 가로 줄을 그을지 말지 결정 문제로 접근해도 된다.
 * 하나의 결정 문제에 영향을 받는 것은 오직 두 사람이고, 양쪽 모서리에 위치한 두 사람은 선택의 여지가 없기 때문이다.
 * 이게 중앙으로 전이되면, 결국 한 컬럼씩 따져보더라도 정해가 나온다.
 *
 * # 접근
 * - 그리디
 * - 시간 복잡도 O(K*N)
 * 1) 위와 아래에서 사다리 타기를 출발해서 중간에 만난다.
 * 2) 중간 지점의 위아래의 불일치 여부를 바탕으로 가로 줄 설치 여부를 결정한다.
 *
 */


class P2469 {

    fun climbLadder(order: CharArray, ladder: Array<CharArray>): CharArray {
        val finalOrder = Array(order.size) { ' ' }
        repeat(order.size) { sc ->
            var c = sc
            repeat(ladder.size) { r ->
                if (c < order.size - 1 && ladder[r][c] == '-') { c++ }
                else if (c > 0 && ladder[r][c-1] == '-') { c-- }
            }
            finalOrder[c] = order[sc]
        }
        return finalOrder.toCharArray()
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val k = readLine().toInt()
        val n = readLine().toInt()
        val targetOrder = readLine().toCharArray()
        val ladder = Array(n) { readLine().toCharArray() }

        // 풀이
        val initialOrder = Array(k) { 'A' + it }.toCharArray()
        val hiddenRowIndex = ladder.indexOfFirst { row -> row[0] == '?' }
        val upperOrder = climbLadder(initialOrder, ladder.sliceArray(0 until hiddenRowIndex))
        val lowerOrder = climbLadder(targetOrder, ladder.sliceArray(hiddenRowIndex + 1 until n).reversedArray())

        val answer = Array(k - 1) { '?' }
        var continueNext = false
        for (c in 0 until k-1) {
            if (continueNext) {
                answer[c] = '*'
                continueNext = false
                continue
            }
            if (upperOrder[c] == lowerOrder[c+1] && upperOrder[c+1] == lowerOrder[c]) {
                answer[c] = '-'
                continueNext = true
            } else if (upperOrder[c] != lowerOrder[c]) {
                answer.fill('x')
                break
            } else {
                answer[c] = '*'
            }
        }

        // 출력
        println(answer.joinToString(""))
    }
}


fun main() = with(P2469()) {
    solve()
}