/**
 *
 * # 발견
 * 완전 탐색을 하게 되면 O(N^2) (N <= 50,000) 으로 시간 초과가 난다.
 * 완전 탐색을 한다는 것은 '연속하는 부분 수열'의 오른쪽 끝을 지정한 뒤,
 * 구간합이 d로 나누어 떨어지게 되는 왼쪽 끝을 모두 찾는 것과 같다.
 * 여기서 누적합에 대한 정보를 저장해 놓으면, O(N)의 탐색 작업을 O(1)로 최적화할 수 있다.
 *
 * # 접근
 * - 누적합
 * - 시간 O(CN)
 * - 공간 O(D+N)
 * 1. 구간의 오른쪽 끝을 나타내는 포인터 r를 오른쪽으로 움직이면서
 *    구간합이 d로 나누어 떨어지도록 하는 왼쪽 끝의 개수를 배열에서 가져와 합산한다.
 *    -> rem = sum(0 to r) % d 에 대해 A[rem]만큼 정답 카운트에 누적시킨다.
 * 2. A[rem]에 r을 추가한다.
 *    -> A[rem]은 현재 구간합 sum(0 to l) % d 가 rem인 l의 개수
 *    
 */


class P3673 {

    fun solve() = with(System.`in`.bufferedReader()) {
        val MAX_D = 1_000_000
        val MAX_N = 50_000
        val prefixRemCounts = IntArray(MAX_D)
        val prefixRems = ArrayList<Int>(MAX_N)

        // 각 테스트케이스에 대해서
        val c = readLine().toInt()
        val sb = StringBuilder()
        repeat(c) {
            // 자료구조 초기화
            prefixRems.forEach { prefixRemCounts[it] = 0 }
            prefixRems.clear()

            // 입력
            val (d, n) = readLine().split(" ").map(String::toInt)
            val nums = readLine().split(" ").map(String::toInt).toIntArray()

            // 풀이
            var answer = 0 // 정답이 정수 범위를 벗어나지 않음
            var prefixRemainder = 0
            prefixRemCounts[0] = 1
            repeat(n) { r ->
                prefixRemainder = (prefixRemainder + nums[r]) % d
                answer += prefixRemCounts[prefixRemainder]++
                prefixRems.add(prefixRemainder)
            }

            // 출력
            sb.append(answer).append('\n')
        }
        print(sb)
    }

}

fun main() = with(P3673()) {
    solve()
}