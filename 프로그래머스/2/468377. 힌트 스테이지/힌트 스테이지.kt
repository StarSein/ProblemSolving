/*

각 스테이지별 힌트 번들 구매 여부에 따라 발생 가능한 경우의 수는 최대 2^15 = 약 32만
각각의 경우에 대해 총 비용을 구하고 그 중 최솟값을 반환하면 된다.

*/
import kotlin.math.min


class Solution {
    
    fun recur(i: Int, totalCost: Int, hintCounts: IntArray, cost: Array<IntArray>, hint: Array<IntArray>): Int {
        if (i == cost.size) return totalCost
        
        val curHintCount = min(hintCounts[i], cost[i].lastIndex)
        val curCost = cost[i][curHintCount]
        val res1 = recur(i + 1, totalCost + curCost, hintCounts, cost, hint)
        
        if (i == cost.size - 1) return res1
        
        val curHintBundle = hint[i]
        val newHintIndices = (1..curHintBundle.lastIndex).map { curHintBundle[it] - 1 }
        newHintIndices.forEach { hintCounts[it]++ }
        val res2 = recur(i + 1, totalCost + curCost + curHintBundle[0], hintCounts, cost, hint)
        newHintIndices.forEach { hintCounts[it]-- }
        
        return min(res1, res2)
    }
    
    fun solution(cost: Array<IntArray>, hint: Array<IntArray>): Int {
        val answer = recur(0, 0, Array(cost.size) { 0 }.toIntArray(), cost, hint)
        return answer
    }
}