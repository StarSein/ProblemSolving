class P14941 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()
        val qArr = Array(n) { readLine().split(" ").map { it.toInt() } }
            .map { it[0] to it[1] }

        // 풀이
        val maxNum = 100_000
        val isPrime = BooleanArray(maxNum + 1) { true }
        isPrime[1] = false
        for (i in 2..maxNum) {
            if (isPrime[i] && i <= maxNum / i) {
                var j = i * i
                while (j <= maxNum) {
                    isPrime[j] = false
                    j += i
                }
            }
        }

        val oddSuffixSum = LongArray(maxNum + 2) { 0L } // 오른쪽에서부터 순회 시 홀수 번째 위치한 소수들의 누적합
        val evenSuffixSum = LongArray(maxNum + 2) { 0L }
        val oddPrime = IntArray(maxNum + 2) // oddPrime[i]: i 이상인 홀수 번째 소수의 최솟값
        val evenPrime = IntArray(maxNum + 2)
        var isOddTurn = true
        for (i in maxNum downTo 1) {
            oddSuffixSum[i] = oddSuffixSum[i + 1]
            evenSuffixSum[i] = evenSuffixSum[i + 1]
            oddPrime[i] = oddPrime[i + 1]
            evenPrime[i] = evenPrime[i + 1]
            if (isPrime[i]) {
                if (isOddTurn) {
                    oddSuffixSum[i] += i.toLong()
                    oddPrime[i] = i
                } else {
                    evenSuffixSum[i] += i.toLong()
                    evenPrime[i] = i
                }
                isOddTurn = !isOddTurn
            }
        }

        val sb = StringBuilder()
        qArr.forEach { (a, b) ->
            val (plus3x, minus) = if (oddPrime[a] <= evenPrime[a]) oddSuffixSum to evenSuffixSum
            else evenSuffixSum to oddSuffixSum

            val answer = 3 * (plus3x[a] - plus3x[b + 1]) - (minus[a] - minus[b + 1])
            sb.append(answer).append('\n')
        }

        // 출력
        print(sb)
    }
}

fun main() = with(P14941()) {
    solve()
}