import kotlin.math.min

/**
 *
 * # 접근
 * 1. 완전 탐색 & 그리디 - O(N^4)
 *  - 울타리의 좌상단 모서리 좌표와 우하단 모서리 좌표를 완전 탐색한다. 즉 40개의 좌표 중 2개를 뽑는 선택을 동시에 2번 하는 것이다.
 *  - 해당 영역 외부의 나무는 모두 벌목해야 한다.
 *    이 벌목으로 얻을 수 있는 울타리의 개수를 세고, 부족하다면 아직 벌목되지 않은 나무 중 가장 긴 것부터 벌목한다.
 *    모든 나무를 벌목해도 해당 영역을 감싸는 울타리의 길이를 채울 수 없는 예외를 미리 처리하자. (울타리 길이 총합 미리 계산 및 비교)
 *
 */

private class P1047 {

    class Tree(
        val row: Int,
        val col: Int,
        val len: Int
    ) {
        fun isInsideFence(sr: Int, er: Int, sc: Int, ec: Int): Boolean {
            return row in sr..er && col in sc..ec
        }
    }

    var N = 0
    var trees = emptyList<Tree>()
    var answer = 40

    fun readInput() = with(System.`in`.bufferedReader()) {
        N = readLine().toInt()

        trees = List(N) {
            val args = readLine().split(" ").map { it.toInt() }
            Tree(args[0], args[1], args[2])
        }
    }

    fun solve() {
        // 정답을 나무의 개수로 초기화한다 (울타리의 넓이가 0인 경우)
        answer = N - 1

        // 울타리 길이 총합을 계산한다
        val sumLen = trees.sumOf { tree -> tree.len }

        // 나무 리스트를 길이의 내림차순으로 정렬한다
        trees = trees.sortedByDescending { tree -> tree.len }

        // 나무 좌표의 리스트를 만든다
        val rows = trees.map { tree -> tree.row }.sorted()
        val cols = trees.map { tree -> tree.col }.sorted()

        // 울타리 영역으로 가능한 선택지를 모두 완전 탐색한다
        for (si in 0 until N-1) {
            val sr = rows[si]
            for (ei in si+1 until N) {
                val er = rows[ei]
                for (sj in 0 until N-1) {
                    val sc = cols[sj]
                    for (ej in sj+1 until N) {
                        val ec = cols[ej]
                        // if. 울타리 영역의 둘레가 울타리 길이 총합보다 크다면 불가능한 것으로, 건너뛴다 (모든 나무를 잘라야 하는 경우 포함)
                        val fenceLen = 2 * (ec - sc + er - sr)
                        if (fenceLen >= sumLen) {
                            break
                        }

                        // 울타리 영역 외부의 나무는 모두 벌목한다
                        // 이에 따라 '벌목 횟수'와 '울타리 길이'를 집계한다
                        var cutCount = 0
                        var curLen = 0
                        trees.forEach { tree ->
                            if (!tree.isInsideFence(sr, er, sc, ec)) {
                                cutCount++
                                curLen += tree.len
                            }
                        }

                        // while. 부족한 둘레가 채워질 때까지 아직 벌목되지 않은 나무 중 가장 긴 것부터 벌목하고 '벌목 횟수'와 '울타리 길이'를 증가시킨다
                        var i = 0
                        while (curLen < fenceLen) {
                            val tree = trees[i++]
                            if (tree.isInsideFence(sr, er, sc, ec)) {
                                cutCount++
                                curLen += tree.len
                            }
                        }

                        // 정답을 '벌목 횟수'의 최솟값으로 갱신한다
                        answer = min(answer, cutCount)
                    }
                }
            }
        }
    }

    fun writeOutput() {
        println(answer)
    }
}

fun main() = with(P1047()) {
    readInput()
    solve()
    writeOutput()
}