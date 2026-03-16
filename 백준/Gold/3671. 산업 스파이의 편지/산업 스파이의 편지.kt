class P3671 {

    val isPrime = BooleanArray(10_000_000) { true }
    val primeSet = HashSet<Int>()
    var inp = intArrayOf()
    val selected = BooleanArray(7) { false }

    fun recur(i: Int, num: Int) {
        if (i == inp.size) return

        for (j in 0..inp.lastIndex) {
            if (selected[j]) continue
            val nextNum = 10 * num + inp[j]
            if (isPrime[nextNum]) {
                primeSet.add(nextNum)
            }
            selected[j] = true
            recur(i + 1, nextNum)
            selected[j] = false
        }
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 전처리
        isPrime[0] = false
        isPrime[1] = false
        for (i in 2..isPrime.lastIndex) {
            if (isPrime[i]) {
                var j = i * 2
                while (j <= isPrime.lastIndex) {
                    isPrime[j] = false
                    j += i
                }
            }
        }

        // 입력
        val c = readLine().toInt()
        // 각 테케에 대해 입력 및 풀이
        val sb = StringBuilder()
        repeat(c) {
            primeSet.clear()

            inp = readLine().map { it - '0' }.toIntArray()
            recur(0, 0)

            sb.append(primeSet.count()).append('\n')
        }

        // 출력
        print(sb)
    }
}


fun main() = with(P3671()) {
    solve()
}