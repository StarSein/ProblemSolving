import kotlin.math.exp

/**
 *
 * # 발견
 * 1. (K+1)로 나누어 떨어지지 않는 키 => 단말 노드로서 높이가 0이다.
 *    (K+1)^2로 나누어 떨어지지 않는 키 => 높이가 1이다.
 *    (K+1)^3로 나누어 떨어지지 않는 키 => 높이가 2이다.
 *    ...
 *    (K+1)^(H+1)로 나누어 떨어지지 않지만, (K+1)^H 로는 나누어 떨어지는 키 => 높이가 H이다.
 * 2. 이러면 O(HQ)로 모든 쿼리를 처리하기 위한 두 가지 작업 중 하나를 구현할 수 있게 되었다.
 *    1) 임의의 키가 주어질 때, 해당 파란색 노드의 높이를 구한다.
 *    2) 높이가 같은 임의의 두 키가 서로 같은 부모를 갖고 있는지 확인한다.
 * 3. 2-2)는 임의의 두 키를 a, b라고 하고 높이가 h라고 할 때 (K+1)^(h+1)로 나눈 몫이 동일하면 부모가 같은 것이다.
 *
 * # 접근
 * - 최소 공통 조상
 * - 시간 복잡도 O(HQ)
 * - 공간 복잡도 O(1)
 * 1. 쿼리가 주어질 때마다 응답을 계산한다
 *  1) 우선 두 키의 높이를 구한다
 *  2) 두 키의 높이를 일치시킨다
 *  3) 두 키의 부모를 일치시킨다
 *  4) 그 과정에서 트리를 거슬러 올라가면서 지나는 경로의 길이를 합산한다
 *  5) 만약 주어진 키가 (K+1)^H 보다 이상인 경우 -1을 정답으로 출력한다
 *  6) (예제2) 두 키가 같은 경우 0을 출력
 */


class P26216 {

    fun Long.pow(n: Int): Long {
        var ret = 1L
        repeat(n) {
            ret *= this
        }
        return ret
    }

    fun getHeight(key: Long, base: Long): Int {
        var ret = 0
        var div = base
        while (key % div == 0L) {
            ret++
            div *= base
        }
        return ret
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        val (k, h, q) = readLine().split(" ").map(String::toInt)
        val base = (k + 1).toLong()
        val maxKey = base.pow(h)
        val sb = StringBuilder()
        // 각각의 쿼리에 대해
        repeat(q) {
            // 입력
            val (a, b) = readLine().split(" ").map(String::toLong)

            // 풀이
            if (a >= maxKey || b >= maxKey) {
                sb.append("-1\n")
                return@repeat
            }
            if (a == b) {
                sb.append("0\n")
                return@repeat
            }

            var ha = getHeight(a, base)
            var hb = getHeight(b, base)
            if (ha < hb) {
                val tmp = ha
                ha = hb
                hb = tmp
            }

            var hc = ha + 1 // 최종적으로 최소 공통 조상의 높이가 할당될 예정
            var div = base.pow(hc)
            while (a / div != b / div) {
                hc++
                div *= base
            }

            val answer = (ha - hb) + 2 * (hc - ha)

            // 출력
            sb.append(answer).append('\n')
        }
        print(sb)
    }

}

fun main() = with(P26216()) {
    solve()
}