/**
 *
 * # 전제
 * N번째 논 위의 볏단은 움직일 수 없다
 * (N-1)번째 논 위의 볏단은 무조건 상대에게 줘야 한다
 * 1,2,...,(N-2)번째 논 위의 볏단은 최적 선택이 가능하다
 *
 * # 접근
 * 1. 그리디 - 시간 O(N), 공간 O(N)
 * 형은 a_N + b_{N-1}, 아우는 b_N + a_{N-1}을 갖게 되는데
 * 두 값 중 더 큰 쪽에 더 많은 볏단을 주는 작업을 1,2,...,(N-2)번째 논 각각에서 수행한다
 *
 */

fun main() = with(System.`in`.bufferedReader()) {
    // 입력을 받는다
    val N = readLine().toInt()
    val a = readLine().split(" ").map(String::toInt).toIntArray()
    val b = readLine().split(" ").map(String::toInt).toIntArray()

    // 더 많은 볏단을 몰아줄 쪽을 고른다
    var (smallSum, largeSum) = minmax(a[N-1] + b[N-2], b[N-1] + a[N-2])

    // 볏단을 배분한다
    repeat(N-2) {
        val (small, large) = minmax(a[it], b[it])
        smallSum += small
        largeSum += large
    }

    // 정답을 출력한다
    val answer = largeSum - smallSum
    println(answer)
}

fun minmax(p: Int, q: Int) = if (p <= q) p to q else q to p
