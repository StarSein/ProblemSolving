import java.util.Scanner

/**
 *
 * # 발견
 * N=18 -> 99
 * N=36 -> 9999
 * N=40 -> 99499
 * N=46 -> 995599
 *
 * # 접근
 * - 그리디
 * - 시간 O(1)
 * - 공간 O(1)
 * 1. N을 18로 나눈 몫의 두 배만큼 자릿수가 늘어난다. (모두 양쪽의 9로 표현)
 * 2. N을 18로 나눈 나머지를 갖고 처리한다.
 *   1) 나머지가 9 이하인 경우 자릿수 1 추가
 *   2) 그렇지 않은 경우 짝수이면 자릿수 2 추가
 *                   홀수이면 자릿수 3 추가
 *
 * # 시도
 * 1. WA: 반례) N=18 일 때 3 출력 (정답 2)
 *
 */

class P25180 {
    fun solve() {
        val N = Scanner(System.`in`).nextInt()

        val remainder = N % 18
        val addition = if (remainder == 0) 0
        else if (remainder <= 9) 1
        else if (remainder % 2 == 0) 2
        else 3

        val answer = 2 * (N / 18) + addition
        println(answer)
    }
}

fun main() = with(P25180()) {
    solve()
}