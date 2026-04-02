import kotlin.math.abs
import kotlin.math.max

class P15809 {


    val maxN = 100_001
    val army = Array(maxN) { 0 }
    val root = Array(maxN) { it }
    val rank = Array(maxN) { 1 }

    fun findRoot(x: Int): Int {
        if (root[x] == x) return x
        return findRoot(root[x]).also { root[x] = it }
    }

    fun union(o: Int, p: Int, q: Int) {
        var rp = findRoot(p)
        var rq = findRoot(q)

        if (rank[rp] < rank[rq]) {
            val tmp = rp
            rp = rq
            rq = tmp
        }

        val na = if (o == 1) army[rp] + army[rq]
                 else abs(army[rp] - army[rq])

        root[rq] = rp
        rank[rp] = max(rank[rp], rank[rq] + 1)
        army[rq] = 0
        army[rp] = na
    }


    fun solve() = with(System.`in`.bufferedReader()) {
        // 국가 정보 입력 처리
        val (n, m) = readLine().split(" ").map { it.toInt() }
        for (i in 1..n) { army[i] = readLine().toInt() }

        // 관계 입력 처리
        repeat(m) {
            val (o, p, q) = readLine().split(" ").map { it.toInt() }
            union(o, p, q)
        }

        // 출력
        army.slice(1..n)
            .filter { it != 0 }
            .let {
                println(it.count())
                println(it.sorted().joinToString(" "))
            }
    }
}


fun main() = with(P15809()) {
    solve()
}