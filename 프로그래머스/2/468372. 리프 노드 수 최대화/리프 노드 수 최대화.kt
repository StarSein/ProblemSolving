/*

1. 완전 트리에서 리프 노드의 개수는 split의 값과 같다.
2. split = 2^a * 3^b 라고 하면 해당하는 완전 트리에서 분배 노드의 개수는
   최소 1+2+4+...+2^a+2^a*3+2^a*9+...+2^a*3^(b-1) 이다.
   즉 트리의 상위 깊이를 degree 2로 채우고, 나머지 하위 깊이를 degree 3으로 채우면
   주어진 split에 대해 dist가 최소화되는 것이다.
3. 2^a * 3^b <= split_limit 을 만족하는 모든 (a, b)에 대해,
   dist를 최소화하여 완전 트리를 만든 다음, dist_limit을 초과하지 않는 선에서 분배 노드를 한 깊이만큼만 추가해 보자.
   (split을 limit에 딱 맞게 잡아놓고 dist_limit 초과분을 빼는 작업은 복잡하다.
    반면, split을 limit의 1/2 또는 1/3 수준으로 잡아놓고 dist_limit 잔여분을 더하는 작업은 간단하다.)
   
1은 split 제약 하에 최대한의 리프 노드를 만드는 방법이다.
2은 split 제약 하에 최소한의 분배 노드를 사용하는 방법이다.
3은 이 방법으로 가능한 모든 대안을 고려한다.
이 풀이로 정답을 도출할 수 있다.
   
*/
import kotlin.math.*


class Solution {
    
    val maxExp = 50
    val dp = Array(maxExp) { LongArray(maxExp) { 0L } }
    
    fun getDist(a: Int, b: Int): Long {
        var dist = 1L
        var width = 1L
        
        if (b == 0) {
            repeat(a - 1) { width *= 2; dist += width }
        } else {
            repeat(a) { width *= 2; dist += width }
            repeat(b - 1) { width *= 3; dist += width }
        }
        
        return dist
    }
    
    fun getSplit(a: Int, b: Int): Long {
        var split = 1L
        
        repeat(a) { split *= 2 }
        repeat(b) { split *= 3 }
        
        return split
    }
    
    fun recur(a: Int, b: Int, dl: Long, sl: Long): Long {
        if (dp[a][b] != 0L) return dp[a][b]
        val dist = getDist(a, b)
        val split = getSplit(a, b)
        if (dist > dl || split > sl) return 1L.also { dp[a][b] = it }
        
        val div = sl / split
        if (div == 1L) {
            return split.also { dp[a][b] = it }
        }
        if (div == 2L) {
            val cur = split + min(split, dl - dist)    
            val next = recur(a + 1, b, dl, sl)
            return max(cur, next).also { dp[a][b] = it }
        }
        val cur = split + 2 * min(split, dl - dist)
        val next = max(recur(a + 1, b, dl, sl), recur(a, b + 1, dl, sl))
        return max(cur, next).also { dp[a][b] = it }
    }
    
    fun solution(dist_limit: Int, split_limit: Int): Int {
        val answer = recur(0, 0, dist_limit.toLong(), split_limit.toLong())
        return answer.toInt()
    }
}