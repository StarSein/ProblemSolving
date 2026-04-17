import java.util.PriorityQueue

/**
 *
 * 1. 처음엔 N개의 정점을 선택하여 A값의 합을 구한다
 * 2. 리프 노드부터 우선순위 큐에 담아놓고 가장 작은 값부터 제거한다
 * 3. 제거된 노드의 부모 노드를 우선순위 큐에 추가한다
 * 4. A값의 합을 구한다
 * 5. 2-4를 반복한다
 *
 */


class P30108 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()
        val pArr = IntArray(n + 1)
        readLine().split(" ").map(String::toInt).forEachIndexed { i, v -> pArr[i+2] = v }
        val aArr = LongArray(n + 1)
        readLine().split(" ").map(String::toInt).forEachIndexed { i, v -> aArr[i+1] = v.toLong() }

        // 풀이
        val childCnt = IntArray(n + 1) { 0 }
        pArr.forEach { childCnt[it]++ }


        val pq = PriorityQueue<Pair<Int, Long>>(Comparator.comparingLong { it.second })
        (1..n).filter { childCnt[it] == 0 }
            .forEach { pq.offer(it to aArr[it]) }

        val maxSums = LongArray(n + 1)
        var aSum = aArr.sum()
        repeat(n) {
            maxSums[n-it] = aSum

            val (node, aVal) = pq.poll()
            aSum -= aVal

            val parent = pArr[node]
            if (--childCnt[parent] == 0) {
                pq.offer(parent to aArr[parent])
            }
        }

        // 출력
        val sb = StringBuilder()
        (1..n).forEach { sb.append(maxSums[it]).append('\n') }
        print(sb)
    }

}


fun main() = P30108().solve()