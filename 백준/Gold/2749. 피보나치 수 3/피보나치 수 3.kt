/**
 * 
 * # 발견
 * [F(n+1)] = [1 1] [F(n)  ]
 * [F(n)  ]   [1 0] [F(n-1)]
 * 
 * A = [1 1] 이라고 하자.
 *     [1 0]
 * F(n) 은 A^n의 2행 1열에 위치한 원소
 * 
 *
 * # 접근
 * - 분할 정복을 이용한 거듭제곱 (n <= 10^12 이므로) 
 * - 시간 O(logN)
 * - 공간 O(1)
 */


class P2749 {
    
    class Matrix(
        val a11: Long,
        val a12: Long,
        val a21: Long,
        val a22: Long
    )
    
    val mod = 1_000_000L
    
    operator fun Matrix.times(other: Matrix) =
        Matrix(
            (a11 * other.a11 + a12 * other.a21) % mod,
            (a11 * other.a12 + a12 * other.a22) % mod,
            (a21 * other.a11 + a22 * other.a12) % mod,
            (a21 * other.a21 + a22 * other.a22) % mod
        )

    fun Matrix.pow(i: Long): Matrix {
        if (i == 1L) return this
        val res = this.pow(i / 2)
        return if (i % 2 == 1L) res * res * this
        else res * res
    }
    
    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toLong()
        
        // 풀이
        val base = Matrix(1, 1, 1, 0)
        val answer = base.pow(n).a21
        
        // 출력
        println(answer)
    }
}

fun main() = with(P2749()) {
    solve()
}