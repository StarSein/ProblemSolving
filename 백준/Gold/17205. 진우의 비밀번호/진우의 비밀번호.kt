/**
 *
 * ex1)
 * 3
 * ba
 * - a가 맨처음에 오는 후보의 개수: 26^2 + 26^1 + 26^0 = 676 + 26 + 1 = 703
 * - b의 순번: 704,  ba의 순번: 705
 *
 * ex2)
 * 6
 * b
 * - a가 맨처음에 오는 후보의 개수: 26^5 + 26^4 + 26^3 + 26^2 + 26^1 + 26^0 =  12,356,631
 * - b의 순번: 12,356,632
 *
 */


class P17205 {

    var n = 0
    var pw = ""

    fun readInput() = with(System.`in`.bufferedReader()) {
        n = readLine().toInt()
        pw = readLine().trim()
    }

    /**
     * 비밀번호 최대 길이가 i일 때, x 하나로만 이뤄진 비밀번호의 순번
     */
    fun calcPrevComb(x: Char, i: Int): Long {
        var ret = 0L
        var pow = 1L
        repeat(i) {
            ret += pow
            pow *= 26L
        }
        ret *= (x - 'a')
        ret++
        return ret
    }

    fun solve() {
        val answer = pw
            .mapIndexed { i, c -> calcPrevComb(c, n - i) }
            .sum()
        println(answer)
    }

}


fun main() = with(P17205()) {
    readInput()
    solve()
}