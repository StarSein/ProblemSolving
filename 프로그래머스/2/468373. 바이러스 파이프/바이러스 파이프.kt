/*

파이프 개봉 순서로 가능한 3 * 2^(k-1) 가지 경우의 수 각각에 대해
너비 우선 탐색을 하여 감염된 배양체 개수를 찾고,
그 중 최댓값을 정답으로 반환하면 된다.
- 시간 복잡도: O(N * 2^K)

재귀 함수 구현
0) 재귀 깊이가 k에 이르면, 감염 배양체 수를 반환
1) 개봉할 파이프 선택
2) 개봉에 따른 인접 배양체 감염 (BFS) - tmp 리스트에 이번에 추가된 감염체 저장해 뒀다가, 반환 시 삭제 처리
3) 재귀 호출 트리 상 자식 노드들의 반환값 중 최댓값 반환

*/
import kotlin.math.max


class Solution {
    
    fun recur(depth: Int, pipeType: Int, infections: MutableList<Int>, graph: Array<Array<MutableList<Int>>>): Int {
        if (depth == 0) return infections.size
        val infectionSize = infections.size
        var maxCount = 0
        (1..3).filter { it != pipeType }.forEach { type ->
            val curGraph = graph[type]
            val visited = BooleanArray(curGraph.size) { false }
            val dq = ArrayDeque<Int>()
            infections.forEach { dq.addLast(it); visited[it] = true }
            while (dq.isNotEmpty()) {
                val curNode = dq.removeFirst()
                curGraph[curNode].filter { !visited[it] }.forEach { nextNode ->
                    dq.addLast(nextNode)
                    visited[nextNode] = true
                    infections.add(nextNode)
                }
            }
            val diff = infections.size - infectionSize
            
            maxCount = max(maxCount, recur(depth-1, type, infections, graph))
            
            repeat(diff) { infections.removeLast() }
        }
        return maxCount
    }
    
    fun solution(n: Int, infection: Int, edges: Array<IntArray>, k: Int): Int {
        // 열린 파이프 타입별 그래프를 만든다
        // graph[i]: 파이프 i가 열렸을 때의 그래프 (i = 0, 1, 2)
        val graph = Array(4) { Array(n+1) { mutableListOf<Int>() } }
        edges.forEach { (x, y, type) ->
            graph[type][x].add(y)
            graph[type][y].add(x)
        }
        
        // 재귀 함수 호출로 정답을 계산한다
        val answer = recur(k, -1, mutableListOf(infection), graph)
        return answer
    }
}