class P10166 {

    fun gcd(a: Int, b: Int): Int =
        if (b == 0) a
        else gcd(b, a % b)

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (d1, d2) = readLine().split(" ").map(String::toInt)

        // 풀이
        val maxD = 2000
        val isOccupied = Array(maxD + 1) { BooleanArray(maxD + 1) { false } }
        var answer = 0
        for (d in d1..d2) {
            for (i in 1..d) {
                val g = gcd(i, d)
                val p = i / g
                val q = d / g
                if (isOccupied[p][q]) continue
                isOccupied[p][q] = true
                answer++
            }
        }

        // 출력
        println(answer)
    }
}


fun main() = with(P10166()) {
    solve()
}