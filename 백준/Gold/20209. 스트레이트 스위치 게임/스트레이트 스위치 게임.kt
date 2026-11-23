import kotlin.math.min

class P20209 {

    var n = 0
    var k = 0
    var aArr = IntArray(8)
    var bArr = Array(8) { intArrayOf() }
    val pushCounts = IntArray(8)

    fun readInput() = with(System.`in`.bufferedReader()) {
        val tokens = readLine().split(" ")
        n = tokens[0].toInt()
        k = tokens[1].toInt()
        aArr = readLine().split(" ").map(String::toInt).toIntArray()
        repeat(k) { sn ->
            val tkns = readLine().split(" ").map { it.toInt() - 1 }
            bArr[sn] = tkns.subList(1, tkns.size).toIntArray()
        }
    }

    fun recur(switchNo: Int): Int {
        if (switchNo == k) {
            // 0번부터 하나씩 스위치 누르는 횟수를 할당하여,
            // k-1번 스위치까지 완료할 경우
            // 스위치 누르는 횟수 배열을 시뮬레이션하여
            // 모든 큐브의 숫자를 동일하게 만들었는지 확인한다
            // 동일하게 된 경우 스위치 누른 횟수의 총합을,
            // 그렇지 않은 경우 INF을 반환한다
            val cubeNoArr = aArr.copyOfRange(0, n)
            pushCounts.forEachIndexed { sn, pc ->
                bArr[sn].forEach { cn ->
                    cubeNoArr[cn] = (cubeNoArr[cn] + (sn + 1) * pc) % 5
                }
            }

            return if (cubeNoArr.distinct().count() == 1) pushCounts.sum()
            else Int.MAX_VALUE
        }
        // 현재 스위치 번호에 0~4까지의 횟수를 한 번씩 할당해 본다
        // 재귀 호출을 통해 모든 경우의 수를 다 따져본다
        var res = Int.MAX_VALUE
        for (pushCount in 0..4) {
            pushCounts[switchNo] = pushCount
            res = min(res, recur(switchNo + 1))
        }
        return res
    }

    fun solve() {
        val answer = recur(0)
        println(if (answer != Int.MAX_VALUE) answer else "-1")
    }
}


fun main() = with(P20209()) {
    readInput()
    solve()
}