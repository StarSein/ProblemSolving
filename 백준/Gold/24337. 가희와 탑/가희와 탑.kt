import java.util.Scanner

/**
 *
 * 1. 건물 높이가 동일할 수 있다. 서로 같은 높이의 건물은 서로 가린다.
 * 2. 왼쪽 건물들의 높이는 최소화 대상이다.
 *    [x] a > b 인 경우, 건물 높이는 왼쪽부터 차례대로 1,2,3,...,a 으로 채우면 된다.
 *    [x] a <= b 인 경우, 건물 높이는 왼쪽부터 차례대로 1,2,3,...,a-1 까지만 채워야 한다.
 * 3. 오른쪽 건물들의 높이도 최소화 대상인데, 내림차순임에 유의해야 한다.
 *    a > b 인 경우, 건물 높이는 오른쪽부터 차례대로 1,2,3,...,b-1 까지만 채워야 한다.
 *    a <= b 인 경우, 건물 높이는 오른쪽부터 차례대로 1,2,3,...,b 까지 채우면 된다.
 * 4. [x] 그 사이 구간의 건물 높이는 모두 1로 설정하면 된다.
 * 5. 단, (a+b-1) > n 인 경우 모순이므로 "-1"을 출력해야 한다.
 * 6. 오른쪽 구간의 나열을 끝낸 다음, 그 바로 옆에 높이 2 이상의 건물을 오름차순 배치해야 한다
 *    a > b 인 경우, 2,3,...,a 를 붙이면 된다.
 *    a <= b 인 경우, 2,3,...,a-1,b 를 붙이면 된다.
 *    이처럼 왼쪽에는 연속된 높이 1 건물의 구간을 최대한 길게 만들어야 한다.
 * WA. a 가 1 인 경우 왼쪽에는 연속된 높이 b 건물로 채워야 한다.
 * WA2. a 가 1 인 경우에는 맨 왼쪽만 높이 b 건물로 채우고 나머지 영역은 높이 1 건물로 채워야 한다.
 *      그리고 이전의 조건 분기를 수정해야 한다.
 *      a <= b 이면서 a != 1 인 경우에는 2,3,...,a-1,b 를 붙이고,
 *      a <= b 이면서 a == 1 인 경우에는 1,1,...,1,1 를 붙인다.
 *      
 */

private class P24337 {

    var N = 0
    var a = 0
    var b = 0
    var answer = IntArray(0)

    fun readInput() = with(Scanner(System.`in`)) {
        N = nextInt()
        a = nextInt()
        b = nextInt()
    }

    fun solve() {
        if (a+b-1 > N) {
            answer = IntArray(1) { -1 }
            return
        }
        answer = IntArray(N) { 1 }

        repeat(b-1) {
            answer[N-1-it] = it+1
        }
        var ptr = N-b
        var tmp = a
        if (a == 1) {
            answer[0] = b
        } else if (a <= b) {
            answer[ptr--] = b
            tmp--
        }
        while (tmp > 1) {
            answer[ptr--] = tmp--
        }
    }

    fun writeOutput() {
        println(answer.joinToString(" "))
    }
}

fun main() = with(P24337()) {
    readInput()
    solve()
    writeOutput()
}