/**
 *
 * 빈 칸의 개수가 2개 이하가 될 경우 적어도 2줄 이상이 완성된다
 * 모든 빈 칸은 자신이 유일한 빈 칸인 가로줄 또는 세로줄을 적어도 한 줄 이상 갖는다 (빈 칸의 개수가 3개 이하이므로)
 *
 * 1. 빈 칸이 3개일 경우 그 중 하나에 1부터 20_000까지 임의의 값을 넣어본다
 * 2. 완성된 줄의 합을 기준으로 남은 빈 칸을 채우기를 시도한다
 *  1) 완성된 줄의 합 S를 구한다
 *  2) 빈 칸 하나인 줄을 기준으로 S를 만들기 위한 나머지 빈 칸을 채운다
 *  3) 8개의 줄에 대해 합이 S인지 검증한다
 * 3. 1과 2를 반복한다
 *
 * 로직
 * 1. 각 빈 칸별로 (r, c, 자신만 유일한 빈 칸인 줄에서 다른 두 수의 합)을 구한다
 * 2. 그 중 한 칸에 1 ~ 20_000 값을 넣어보면서 S 값을 설정한다
 * 3. S 값을 기준으로 나머지 빈 칸(들)에 값을 채운다
 * 4. 8개의 줄에 대해 합이 S인지 검증한다
 *
 */


class P2045 {

    class Blank(
        val r: Int,
        val c: Int,
        val twoSum: Int
    )

    fun solve() = with(System.`in`.bufferedReader()) {
        val maxNum = 20_000
        val sz = 3
        val indices = 0..<sz

        // 입력
        val grid = Array(3) { readLine().split(" ").map { it.toInt() }.toIntArray() }

        // 풀이
        val blanks = mutableListOf<Blank>()
        for (r in indices) {
            val row = grid[r]
            if (row.count { it == 0 } == 1) {
                val c = row.indexOf(0)
                blanks.add(Blank(r, c, row.sum()))
            }
        }
        for (c in indices) {
            val col = indices.map { grid[it][c] }
            if (col.count { it == 0 } == 1) {
                val r = col.indexOf(0)
                if (blanks.any { it.r == r && it.c == c }) continue
                blanks.add(Blank(r, c, col.sum()))
            }
        }
        if (blanks.isEmpty()) {
            grid.forEach { println(it.joinToString(" ")) }
            return
        }

        val firstBlank = blanks[0]
        val otherBlanks = blanks.subList(1, blanks.size)
        for (i in 1..maxNum) {
            grid[firstBlank.r][firstBlank.c] = i
            val threeSum = i + firstBlank.twoSum

            otherBlanks.forEach { otherBlank ->
                grid[otherBlank.r][otherBlank.c] = threeSum - otherBlank.twoSum
            }

            if (grid.any { row -> row.sum() != threeSum }) continue
            if (indices.any { c -> indices.sumOf { r -> grid[r][c] } != threeSum }) continue
            if (indices.sumOf { grid[it][it] } != threeSum) continue
            if (indices.sumOf { grid[it][sz-1-it] } != threeSum) continue

            break
        }

        // 출력
        grid.forEach { println(it.joinToString(" ")) }
    }
}


fun main() = with(P2045()) {
    solve()
}