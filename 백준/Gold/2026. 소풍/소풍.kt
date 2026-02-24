/**
 *
 * # 발견
 * 모든 탐색 지점의 현재 상태에 따라 선발 가능한 학생이 달라지므로,
 * 백트래킹으로 구현해야 할 듯하다.
 * 이때 최대한 가지치기를 하려면 어떻게 해야 할까?
 * 1. 현재까지 선발된 인원 모두와 친구인 인원들을 하나의 Set에 저장하며 관리한다
 *    이를 통해 O(N^2)의 선별 작업을 O(N)으로 최적화할 수 있다
 * 2. 단, 선발했던 친구 A를 제외하는 과정에서
 *    A의 친구들 중 집합에서 제거되어야 할 인원을 파악하기 위해서 별도로 참조 횟수를 관리한다
 *    (key: 번호, value: 선발된 친구의 개수)
 *    아니다. 재귀 함수의 스택에 변경 사항을 저장해 뒀다가
 *    뒤로 돌아갈 때 철회하면 된다.
 *    아니다. 공통 친구 자체를 재귀 함수의 매개 변수로 두면 철회할 필요가 없다.
 *
 * # 접근
 * - 현재 상태
 * 1. node: 최근에 잠정 선발된 노드
 * 2. commons: 현재 선발된 인원들의 공통 친구 Set
 * 3. members: 현재 선발된 인원 List (initialCapacity를 MAX_K로 설정)
 *
 * # 재귀 호출 로직
 * if) members.size == K 인 경우
 *  - members 출력 (정답)
 *  - 함수 리턴 및 모든 재귀호출 중단
 * else)
 *  - 'node의 친구 리스트'에서 node보다 번호가 큰 것들 중, 번호가 작은 것부터 순회하며
 *    이를 next라고 할 때
 *     if) '공통 친구 집합'에 next가 없는 경우
 *      - 가지치기 1
 *     if) '공통 친구 집합'과 'next의 친구 집합'의 교집합이 K보다 작은 경우
 *      - 가지치기 2
 *    - 재귀 호출 이전
 *      (1) members에 next 추가
 *      (2) commons의 변경 처리 및 변경사항 저장 (필요없음)
 *      (3) 재귀 호출
 *      (4) (1,2) 철회
 *
 * # 전체 로직
 * - 친구 관계를 저장한다
 * - 친구 수가 K보다 작은 학생들을 모두 친구 관계에서 제거한다 (boolean 배열로 처리)
 * - 1번 노드부터 N번 노드까지 재귀 호출
 * - members가 비어 있을 경우 -1 출력
 *
 * # 시도
 * 1. WA: 입력으로 주어지는 친구 관계 a b 가 항상 a < b 는 아니라는 걸 간과함
 * 2. MLE: 재귀 호출 스택만큼 쌓이는 집합이 문제였을까?
 *         집합 사용을 없애고 인접 행렬을 이용하자.
 *         이 경우 '가지치기 2'가 사라진다.
 */


class P2026 {

    val MAX_N = 900
    val MAX_K = 62
    var K = 0
    var N = 0
    var F = 0
    val matrix = Array(MAX_N+1) { BooleanArray(MAX_N+1) { false } }

    val excluded = BooleanArray(MAX_N+1) { false }
    val members = ArrayList<Int>(MAX_K)
    val sb = StringBuilder()

    fun recur(node: Int) {
        if (sb.isNotEmpty()) return
        members.add(node)
        if (members.size == K) {
            members.forEach { sb.append(it).append('\n') }
            return
        }
        for (next in node+1 until N+1) {
            if (!matrix[node][next]) continue
            if (members.any { member -> !matrix[member][next] }) continue
            recur(next)
        }
        members.removeLast()
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        readLine().split(" ").map(String::toInt).let {
            K = it[0]
            N = it[1]
            F = it[2]
        }
        repeat(F) {
            var (a, b) = readLine().split(" ").map(String::toInt)
            matrix[a][b] = true
            matrix[b][a] = true
        }

        // 풀이
        for (i in 1..N) {
            matrix[i][i] = true
            if (matrix[i].count { it } < K) {
                excluded[i] = true
            }
        }

        for (i in 1..N) {
            if (excluded[i]) continue
            recur(i)
        }

        // 출력
        if (sb.isEmpty()) {
            sb.append(-1)
        }
        print(sb)
    }
}

fun main() = with(P2026()) {
    solve()
}