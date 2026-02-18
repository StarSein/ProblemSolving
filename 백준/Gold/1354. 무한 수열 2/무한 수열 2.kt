/**
 *
 * ex2)
 * N=12, P=2, Q=3, X=1, Y=0
 * A(1) = A(-1) + A(0) = 2
 * A(2) = A(0) + A(0) = 2
 * A(3) = A(0) + A(1) = 3
 * A(4) = A(1) + A(1) = 4
 * A(5) = A(1) + A(1) = 4
 * A(6) = A(2) + A(2) = 4
 * A(7) = A(2) + A(2) = 4
 * A(8) = A(3) + A(2) = 5
 * A(9) = A(3) + A(3) = 6
 * A(10) = A(4) + A(3) = 7
 * A(11) = A(4) + A(3) = 7
 * A(12) = A(5) + A(4) = 8
 *
 * # 발견
 * 재귀 호출로 정답을 계산한다고 할 때 최악의 케이스는 N=10^13, P=2, Q=3, X=0, Y=1 같은 꼴일테다.
 * 수직선 위의 점 N과 원점 0을 생각해 보면, A(N)을 구하기 위해 A(N/2)과 A(N/3)을 알아야 한다.
 * 즉 관심의 대상이 되는 영역이 1/P로 줄어들고, 1/Q로 줄어드는 것이다.
 * 이에 관심의 대상이 되는 영역은 P 및 Q를 밑으로 하는 로그만큼 감소할 것이라고 추측할 수 있다.
 * 다만 그 과정에서 만나는 모든 노드들에 대해 이진 트리 형태로 분기가 되는 것을 고려하면,
 * 단순 재귀 호출로 구현했을 때의 시간 복잡도를 어떻게 계산해야 할 지 모르겠다.
 *
 * A(N) 계산을 위한 재귀 호출 트리의 높이는 H=logN
 * 완전 이진 트리의 크기는 2^H - 1 이므로, P=2 이고 Q=2 인 경우를 고려하면
 * 전체 시간 복잡도가 O(N)이라는 결과가 나오고, 이는 N=10^13 에 부적절하다.
 * naive한 시간 복잡도 계산에 따르면, 재귀 호출은 정해가 아닌 것처럼 보인다.
 *
 * 다만 재귀 호출을 거듭할 수록 관심의 영역이 계속해서 줄어들고 있는 수직선 상의 그림을 떠올려 보자.
 * '이미 계산한 값'을 그대로 사용해서 중복적인 재귀 호출을 없앨 수 있다면,
 * '새로 계산하는 횟수' 또한 관심의 영역만큼이나 지수적으로 감소할 것이라고 추측된다.
 *
 * # 풀이
 * 1. 재귀 함수로 주어진 점화식을 그대로 구현한다.
 * 2. DP 메모이제이션으로 시간 복잡도를 최적화한다.
 * 3. 좌표계 영역이 넓으므로 계산된 값의 저장을 위해, 선형 자료구조가 아닌 long 타입의 Map을 사용한다.
 *
 */

class P1354 {

    var N = 0L
    var P = 0L
    var Q = 0L
    var X = 0L
    var Y = 0L

    val cache = mutableMapOf<Long, Long>()

    var answer = 0L

    fun readInput() = with(System.`in`.bufferedReader()) {
        readLine().split(" ").map(String::toLong).let {
            N = it[0]
            P = it[1]
            Q = it[2]
            X = it[3]
            Y = it[4]
        }
    }

    fun writeOutput() {
        println(answer)
    }

    fun solve() {
        answer = recur(N)
    }

    fun recur(i: Long): Long {
        if (i <= 0L) {
            return 1L
        }
        return cache.getOrPut(i) {
            recur(i / P - X) + recur(i / Q - Y)
        }
    }
}

fun main() = with(P1354()) {
    readInput()
    solve()
    writeOutput()
}