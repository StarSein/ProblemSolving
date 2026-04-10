/**
 *
 * 100만 번을 초과하는 공격 횟수가 이뤄질 수는 없다
 *
 * 플레이어를 끝까지 반복 순회하면서,
 * 전체에 적용된 데미지를 증가시키고
 * 자신에게만 적용되는 힐량을 증가시킨다 (자신의 공격력만큼)
 * 현재 체력 = (자신의 체력 + 자신의 힐량 - 전체 적용 데미지)
 * 현재 체력이 0 이하일 경우 패자로 전환한다
 *
 * 패자를 효율적으로 건너뛰기 위해
 * 생존자만 저장하는 큐를 사용한다
 *
 */


class P32405 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()
        val deals = readLine().split(" ").map { it.toInt() }.toIntArray()
        val healths = readLine().split(" ").map { it.toInt() }.toIntArray()

        // 풀이
        val recoveries = IntArray(n)
        var entireDamage = 0
        val dq = ArrayDeque(List(n) { it })
        while (dq.size > 1) {
            val p = dq.removeFirst()
            val curHealth = healths[p] + recoveries[p] - entireDamage
            if (curHealth <= 0) continue
            recoveries[p] += deals[p]
            entireDamage += deals[p]
            dq.addLast(p)
        }

        // 출력
        val answer = dq.first() + 1
        println(answer)
    }
}


fun main() = with(P32405()) {
    solve()
}