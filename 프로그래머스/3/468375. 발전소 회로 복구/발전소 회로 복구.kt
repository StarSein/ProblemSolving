/*

1. 가능한 경우의 수는 대략 factorial(15). 선행 조건이 1개만 있다면 고려해야 할 대상이 거의 15!에 수렴한다.
   상태 정보 (현재 노드, 현재까지 활성화한 패널 번호 목록)에 대해 최소 시간만 저장하고 갱신한다면,
   시간 복잡도를 O(K^2 * 2^K) 으로 최적화할 수 있다. 15^2 * 2^15 = 225 * 320000 < 90000000
2. 비트마스킹으로 '활성화된 패널 목록'을 표현하자.
3. 탐색 대상이 많으므로 시간 제약에 맞추기 위해 재귀함수보다는 반복문을 이용하자.

*/

import kotlin.math.abs
import kotlin.math.min

class Solution {
    
    fun solution(h: Int, grid: Array<String>, panels: Array<IntArray>, seqs: Array<IntArray>): Int {
        val inf = 1_000_000
        val n = grid.size
        val m = grid[0].length
        val k = panels.size
        
        // 모든 패널에 대해, 서로간의 최단 거리를 계산하여 저장한다.
        // 이때 엘리베이터를 기준으로 거리를 계산한다.
        // 엘리베이터를 찾는다.
        var sr = -1
        var sc = -1
        repeat(n) { r -> repeat(m) { c -> if (grid[r][c] == '@') { sr = r; sc = c } } }
        // 엘리베이터로부터 모든 지점까지의 거리를 저장한다.
        val dists = Array(n) { Array(m) { inf } }
        val dr = arrayOf(0, 1, 0, -1)
        val dc = arrayOf(1, 0, -1, 0)
        val dq = ArrayDeque<Pair<Int, Int>>()
        var dist = 0
        dq.addLast(sr to sc)
        dists[sr][sc] = 0
        while (dq.isNotEmpty()) {
            dist++
            repeat(dq.size) {
                val (r, c) = dq.removeFirst()
                repeat(4) { d ->
                    val nr = r + dr[d]
                    val nc = c + dc[d]
                    if (nr in 0..n-1 && nc in 0..m-1 && grid[nr][nc] == '.' && dists[nr][nc] == inf) {
                        dq.addLast(nr to nc)
                        dists[nr][nc] = dist
                    }
                }
            }
        }
        
        // 모든 패널에 대해 최단 거리를 저장한다.
        val minTimes = Array(k) { IntArray(k) { 0 } }
        (1..k-1).forEach { i ->
            val (sf, sr, sc) = panels[i].map { it - 1 }
            (0..i-1).forEach { j ->
                val (ef, er, ec) = panels[j].map { it - 1 }
                if (sf == ef) {
                    val q = ArrayDeque<Pair<Int, Int>>()
                    val visited = Array(n) { BooleanArray(m) { false } }
                    q.addLast(sr to sc)
                    visited[sr][sc] = true
                    var time = 0
                    while (q.isNotEmpty()) {
                        time++
                        repeat(q.size) {
                            val (r, c) = q.removeFirst()
                            repeat(4) { d ->
                                val nr = r + dr[d]
                                val nc = c + dc[d]
                                if (nr in 0..n-1 && nc in 0..m-1 && grid[nr][nc] != '#' && !visited[nr][nc]) {
                                    if (nr == er && nc == ec) {
                                        minTimes[i][j] = time
                                    }
                                    q.addLast(nr to nc)
                                    visited[nr][nc] = true
                                }
                            }
                        }
                    }
                } else {
                    minTimes[i][j] = abs(sf - ef) + dists[sr][sc] + dists[er][ec]    
                }
                minTimes[j][i] = minTimes[i][j]
            }
        }
        
        // 각 패널의 선행 조건을 비트마스크로 표현한다.
        val conditions = IntArray(k) { 0 }
        seqs.forEach { (a, b) ->
            conditions[b-1] += 1 shl (a-1) // seqs의 원소는 중복되지 않으므로 덧셈 연산 가능
        }
        
        // 메모이제이션으로 최적화된 탐색을 한다.
        // dp[i][j]: 현재 활성화된 패널을 나타내는 비트마스크 정수값을 i, 현재 위치한 패널을 j라고 하자.
        val mbm = (1 shl k) - 1 // maxBitMask
        val dp = Array(mbm+1) { IntArray(k) { inf } }
        dp[0][0] = 0
        repeat(mbm) { i ->
            // 선행 조건을 만족하는 노드만 nexts에 넣는다.
            val nexts = (0..k-1).filter { j ->
                val condition = conditions[j]
                i and (1 shl j) == 0 && // 아직 방문하지 않은 노드
                i and condition == condition // 선행조건이 모두 만족된 노드
            }
            
            val currents = (0..k-1).filter { j -> dp[i][j] != inf } // 방문 완료한 노드 
            currents.forEach { j ->
                val curTime = dp[i][j]
                for (nj in nexts) {
                    val ni = i or (1 shl nj)
                    dp[ni][nj] = min(dp[ni][nj], curTime + minTimes[j][nj])
                }
            }
        }
        
        // dp[2^k - 1].min()을 정답으로 반환한다.
        val answer = dp[mbm].minOf { it }
        return answer
    }
}