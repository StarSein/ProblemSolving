/*

# 예제1
(5, 3) (7, 6)
X % 5 == 3 && X % 7 == 6 만족하는 X의 최솟값 13

# 예제4
(6, 2) (6, 3) (6, 4) (6, 5)
동시에 만족하는 X는 존재할 수 없으므로 -1

# 예제2
Case1) (7, 3) (7, 4) (4, 3) 불가능
Case2) (7, 4) (7, 4) (4, 3)
-> (7, 4) (4, 3) 만족하는 X의 최솟값 11
Case3) 불가능


# 접근
1. 완전 탐색의 경우의 수 18^5 < 20^5 = 3_200_000
  5개 신호등 각각의 나머지를 정해놓고 해를 구하려고 한다면, 시간 초과가 날 듯하다.
2. 5개 신호등 각각의 주기(G+Y+R) 값들의 최소공배수를 구하면 그 안에는 정답이 나와야 한다. 1부터 lcm(주기1, 주기2, ..., 주기5)까지 완전 탐색을 해 보면서 확인하면 된다.
- 최악의 경우: 5개 신호등의 주기가 모두 서로소이면서 가장 큰 경우 < 20^5
3. lcm 만큼 했는데도 안 나오면 -1

6, 10
10, 6
4, 6
6, 4
2, 4
4, 2
0, 2
2, 0

*/


class Solution {
    
    class Signal(
        val period: Int,
        val yellowStart: Int,
        val yellowEnd: Int
    )
    
    fun gcd(a: Int, b: Int): Int {
        if (b == 0) return a
        return gcd(b, a % b)
    }
    
    fun lcm(a: Int, b: Int): Int {
        return a * b / gcd(a, b)
    }
    
    fun solution(signals: Array<IntArray>): Int {
        val sigs = signals.map { 
            Signal(
                period = it[0] + it[1] + it[2],
                yellowStart = it[0] + 1,
                yellowEnd = it[0] + it[1]
            )
        }
        val maxX = sigs.fold(1) { acc, sig -> lcm(acc, sig.period) }
        (1..maxX).forEach { x ->
            if (sigs.all { sig -> x % sig.period in sig.yellowStart..sig.yellowEnd }) {
                return x
            }
        }
        return -1
    }
}