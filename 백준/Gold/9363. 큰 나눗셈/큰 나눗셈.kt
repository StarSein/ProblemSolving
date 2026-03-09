import kotlin.math.ceil
import kotlin.math.sqrt

/**
 *
 * # 발견
 * 1. 분자와 분모의 IntegerOverflow 혹은 LongOverflow 문제를 피하기 위해서는
 *    소인수분해를 해야 한다
 *
 * # 접근
 * - 에라토스테네스의 체
 * - 시간 복잡도 O(TN) (상수 C가 곱해짐: C는 1000보다 작은 소수의 개수)
 * - 공간 복잡도 O(N)
 * 1. 에라토스테네스의 체를 이용해서 소수를 구해놓는다
 * 2. 각 테스트케이스에 대해, A와 B 각각을 소인수분해한다
 * 3. 모든 소수에 대해, A와 B에 존재하는 개수의 차이를 A 또는 B에 배분한다
 * 4. 배분한 소수들을 모두 곱해서 X와 Y를 만든다
 *
 */


class P9363 {

    var primes: IntArray = intArrayOf()

    fun processPrimeFactorization(counts: IntArray, arr: IntArray) {
        arr.forEach { num ->
            var tmp = num
            val threshold = sqrt(num.toFloat()).toInt()
            for (prime in primes) {
                if (prime > threshold) {
                    break
                }
                while (tmp % prime == 0) {
                    counts[prime]++
                    tmp /= prime
                }
            }
            if (tmp > 1) {
                counts[tmp]++
            }
        }
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 소수 찾기
        val maxVal = 1_000_000
        val isPrime = BooleanArray(maxVal + 1) { true }
        isPrime[1] = false
        for (i in 2..maxVal) {
            if (isPrime[i]) {
                var j = 2 * i
                while (j <= maxVal) {
                    isPrime[j] = false
                    j += i
                }
            }
        }
        primes = (2..maxVal).filter { isPrime[it] }.toIntArray()
        val countsA = IntArray(maxVal + 1)
        val countsB = IntArray(maxVal + 1)

        // 입력
        val t = readLine().toInt()
        val sb = StringBuilder()
        // 모든 테스트 케이스에 대해 입력 받고 풀이
        for (tc in 1..t) {
            val (n, m) = readLine().split(" ").map(String::toInt)
            val arrA = readLine().split(" ").map(String::toInt).toIntArray()
            val arrB = readLine().split(" ").map(String::toInt).toIntArray()

            primes.forEach { prime ->
                countsA[prime] = 0
                countsB[prime] = 0
            }

            processPrimeFactorization(countsA, arrA)
            processPrimeFactorization(countsB, arrB)

            var x = 1
            var y = 1
            primes.forEach { prime ->
                val diff = countsA[prime] - countsB[prime]
                if (diff > 0) repeat(diff) { x *= prime }
                else repeat(-diff) { y *= prime }
            }

            sb.append("Case #$tc: $x / $y\n")
        }

        // 출력
        print(sb)
    }
}

fun main() = with(P9363()) {
    solve()
}