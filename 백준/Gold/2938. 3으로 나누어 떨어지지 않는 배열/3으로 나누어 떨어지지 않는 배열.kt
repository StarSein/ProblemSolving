/**
 *
 * # 분석 (3으로 나눈 나머지)
 * 1. 문제 없는 한 쌍
 *  (0, 1)
 *  (0, 2)
 *  (1, 1)
 *  (2, 2)
 * 2. 문제 있는 한 쌍
 *  (0, 0)
 *  (1, 2)
 * 3. 불가능한 경우
 *  1) 0이 ((N+1)/2 + 1)개 이상 있는 경우,
 *     비둘기 집의 원리에 따라 인접한 두 수가 3의 배수인 곳이 적어도 하나 이상 생긴다.
 *  2) 배열에 1과 2 두 종류만 있는 경우
 * 4. 배치 규칙
 *  [x] 1) N이 홀수이면서 0이 (N/2 + 1)개 있는 경우, 0을 가장자리부터 두 칸마다 배치한다. 1, 2는 남는 공간에 아무렇게나 배치한다.
 *  [x] 2) 그렇지 않은 경우, 1...102...2 를 만들고 0을 기준으로 두 칸마다 0을 배치한다.
 *  1) 0을 기준으로 왼쪽은 1, 오른쪽은 2를 배치한다. 단, 0이 남아있을 경우, 0을 배치해도 될 때마다 배치한다.
 * 5. N이 1인 경우는 항상 가능하다.
 * # 접근
 * 1. Deque를 활용한 풀이 - O(N)
 *
 *
 */


private class P2938 {

    var N = 0
    var arr = intArrayOf()
    var nums = Array(3) { ArrayDeque<Int>() }
    var dq = ArrayDeque<Int>()
    var answer = ""

    fun readInput() = with(System.`in`.bufferedReader()) {
        N = readLine().toInt()
        arr = readLine().split(" ").map(String::toInt).toIntArray()
    }

    fun solve() {
        // N이 1인 경우는 항상 가능하다
        if (N == 1) {
            answer = arr[0].toString()
            return
        }

        // 3으로 나눈 나머지별로 원소를 분류한다
        arr.forEach { nums[it % 3].add(it) }

        // 그룹별 개수를 바탕으로, 불가능한 경우에 해당하는지 검증한다
        val (dq0, dq1, dq2) = nums
        if (dq0.size >= (N + 1) / 2 + 1 ||
            dq0.isEmpty() && dq1.isNotEmpty() && dq2.isNotEmpty()) {
            answer = "-1"
            return
        }

        // 배치 규칙을 구현한다
        while (N-- > 0) {
            if (dq0.isNotEmpty()) {
                if (dq.isEmpty() || dq.first() % 3 == 1) {
                    dq.addFirst(dq0.removeFirst())
                    continue
                } else if (dq.last() % 3 == 2) {
                    dq.addLast(dq0.removeFirst())
                    continue
                }
            }
            if (dq1.isNotEmpty()) {
                dq.addFirst(dq1.removeFirst())
                continue
            }
            if (dq2.isNotEmpty()) {
                dq.addLast(dq2.removeFirst())
            }
        }

        answer = dq.joinToString(" ")
    }

    fun writeOutput() {
        println(answer)
    }
}

fun main() = with(P2938()) {
    readInput()
    solve()
    writeOutput()
}
