/**
 *
 * # 발견
 * 1. 상훈이의 최적 선택: 가벼운 물건들부터 최대한 많이 가져간다 (단, 도둑에게 K개 이상의 물건을 남기는 선에서)
 *  1) 도둑의 무게 합 최대화를 위해
 *  2) 도둑은 물건의 수량이 K보다 적으면 여지없이 무거운 물건도 가져가야 하므로
 * 2. 도둑의 최적 선택: 가벼운 물건들부터 가져가서 K개를 맞춘다
 * naive하게 구현하면 시간 복잡도가 O(CN)으로 시간 초과가 날 것이다.
 *
 * 가방의 무게 x에 대해 상훈이가 최적 선택을 해서 가져가는 물건들의 집합을 S(x)라고 하자.
 * 가방의 무게 x1 < x2 에 대해, S(x1)는 항상 S(x2)의 부분집합이다.
 * 즉, x가 커짐에 따라 도둑이 담아갈 수 없게 되는 물건이 발생하는데, 담아갈 수 없다가 담아갈 수 있게 되는 물건은 절대 발생하지 않는다.
 *
 * 따라서 x가 커지면서 어떤 임계점들을 지날 때마다 도둑은 가장 가벼운 물건 하나씩 빼앗기게 되고 이는 돌려받을 수 없다.
 * 최소 힙을 통해 도둑이 선택 가능한 물건들을 담으면 되겠다.
 * 단, 여기서도 naive하게 도둑의 최적 선택을 일일이 구현하면 O(CKlogN)으로 역시 시간 초과가 날 것이다.
 *
 * 도둑이 선택하는 K개의 물건을 크기의 오름차순으로 큐에 넣어놓고, 상훈이한테 한 개 빼앗기면 아직 큐에 들어가지 않은 가장 가벼운 물건을 큐에 넣으면 된다.
 * 이를 구현하면 시간 복잡도는 O(NlogN + C)으로 시간 제약을 충족한다.
 *
 * # 접근
 * - 그리디, 큐, 정렬
 * - 시간 복잡도 O(NlogN + C)
 * - 공간 복잡도 O(N)
 * 1. 자료 구조
 *  - amountSH: 상훈이의 물건들 무게 총합
 *  - amountKB: 기범이의 물건들 무게 총합
 *  - bagKB: 도둑 기범이의 물건들 큐
 *  - bagUN: 누구에게도 선택되지 않은 물건들 큐
 * 2. 알고리즘
 *  1) x가 1씩 커짐에 따라 (x - aSH) == bKB.first() 가 성립하게 되면
 *     bKB.removeFirst() 그리고
 *     bKB.addLast(bUN.removeFirst())
 *  2) 그때마다 aKB를 계산해서 출력한다
 *  3) 단, bUN에 더 이상 물건이 없다면, 1)을 수행하지 않는다
 *
 */


class P34204 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, k, c) = readLine().split(" ").map(String::toInt)
        val items = readLine().split(" ").map(String::toInt)

        // 풀이
        val sortedItems = items.sorted().map(Int::toLong)
        val bagKB = ArrayDeque<Long>(elements = sortedItems.subList(0, k))
        val bagUN = ArrayDeque<Long>(elements = sortedItems.subList(k, n))
        var amountSH = 0L
        var amountKB = bagKB.sum()

        val sb = StringBuilder()
        for (x in 1..c) {
            if (bagUN.isNotEmpty() && x - amountSH == bagKB.first()) {
                amountKB -= bagKB.first()
                amountSH += bagKB.removeFirst()
                amountKB += bagUN.first()
                bagKB.addLast(bagUN.removeFirst())
            }
            sb.append(amountKB).append('\n')
        }

        // 출력
        print(sb)
    }

}

fun main() = with(P34204()) {
    solve()
}