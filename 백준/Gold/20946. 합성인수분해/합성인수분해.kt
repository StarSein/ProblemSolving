import java.util.Scanner

/**
 *
 * # 분석
 * 24 = 2^3 * 3 = (2*2) * (2*3) = 4 * 6
 * 16 = 2^4 = (2*2) * (2*2) = 4 * 4
 * 60 = 2^2 * 3 * 5 = (2*2) * (3*5) = 4 * 15
 *
 * - 소인수분해를 했을 때 나오는 소수의 개수(중복 포함)가 4개 이상이면 합성인수분해가 가능하다.
 * - 가장 작은 소수부터 한 쌍씩 짝지어서 수열의 원소로 만들면, 곧 사전순으로 가장 앞서는 수열이 된다.
 *   단, 소수의 개수가 홀수일 경우 마지막 남은 가장 큰 소수 3개를 수열의 마지막 원소로 만든다.
 *
 * # 접근
 * 1. 소수 판별 - O(sqrtN)
 * - N을 소인수분해를 하고, 소수의 개수가 4개 미만이면 "-1"을 출력한다.
 * - 그렇지 않은 경우, 작은 소수부터 한 쌍씩 묶어서 정답 배열에 넣는다.
 *
 * # WA
 * 1. LongOverflow가 났나? i*i<=n 을 i<=n/i 로 바꿔 보자.
 * 2. 합성수 하나로 이뤄진 수열도 합성인수분해가 된 것이다. 따라서, 소수의 개수가 2개 미만일 경우에만 "-1"을 출력해야 한다.
 */

private class P20946 {

    var n = 0L
    val answer = arrayListOf<Long>()

    fun readInput() = with(Scanner(System.`in`)) {
        n = nextLong()
    }

    fun solve() {
        // 소인수분해
        val primes = arrayListOf<Long>()
        var i = 2L
        while (i <= n / i) {
            while (n % i == 0L) {
                primes.add(i)
                n /= i
            }
            i++
        }
        if (n != 1L) {
            primes.add(n)
        }

        // 합성인수분해
        if (primes.size < 2) {
            answer.add(-1)
            return
        }
        var j = 0
        while (j + 1 < primes.size) {
            answer.add(primes[j] * primes[j + 1])
            j += 2
        }
        if (j == primes.size - 1) {
            answer[answer.lastIndex] *= primes[j]
        }
    }

    fun writeOutput() {
        println(answer.joinToString(" "))
    }
}

fun main() = with(P20946()) {
    readInput()
    solve()
    writeOutput()
}