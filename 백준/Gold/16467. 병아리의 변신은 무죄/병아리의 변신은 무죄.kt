/**
 *
 * K=0
 * -> 2의 거듭제곱
 * K=1
 * 0 - 1
 * 1 - 1 (1)
 * 2 - 2 (1)
 * 3 - 3 (2)
 * 4 - 5 (3)
 * 5 - 8 (5)
 * K=2
 * 0 - 1
 * 1 - 1 (1)
 * 2 - 1 ((1)) (1)
 * 3 - 2 ((1)) (1)
 * 4 - 3 ((1)) (2)
 * 5 - 4 ((2)) (3)
 * 6 - 6 ((3)) (4)
 *
 *
 * # 발견
 * 임의의 K에 대해 f(N)을 N일 후 병아리의 수라고 하자.
 * f(N) = f(N-1) + f(N-K-1) (0 <= K <= 10)
 *
 * N의 범위가 1억 이하이므로, O(N)으로는 시간 초과가 난다.
 * 따라서 점화식을 그대로 구현하는 풀이는 채택할 수 없다.
 * 점화식으로부터 일반항을 구해야 한다.
 *
 * K=2 인 경우를 예시로, 행렬 곱 형태로 나타내면
 *
 * [f(3)]   [1 0 1] [f(2)]       [f(2)]
 * [f(2)] = [1 0 0] [f(1)] = A * [f(1)] 라고 하자
 * [f(1)]   [0 1 0] [f(0)]       [f(0)]
 *
 *       [1 1 1]
 * A^2 = [1 0 1]
 *       [1 0 0]
 *
 *       [2 1 1]
 * A^3 = [1 1 1]
 *       [1 0 1]
 *
 *       [3 1 2]
 * A^4 = [2 1 1]
 *       [1 1 1]
 *
 * f(N) 은 A^N 의 1행 1열에 위치한 원소와 같다.
 * 이는 K=2 뿐만 아니라 모든 K에 대해 성립하는데,
 * i <= K 인 i에 대해 f(i) = 1 이 항상 성립하기 때문이다. (최초의 병아리가 1일차에 낳은 알은 K+1 일차에 병아리가 되기 때문)
 * 이때 A^N 은 크기가 (K+1) X (K+1) 인 행렬
 *
 * A^N 을 구하기 위해 분할정복을 이용하면 O(logN)으로 f(N)을 구할 수 있다
 *
 *
 * # 접근
 * DP, 분할정복을 이용한 거듭제곱
 * 시간 O((T + K^3) * logN)
 * 공간 O(K^2 * logN)
 * 1. 모든 K 값에 대해, A^(2^N) 을 구해 놓는다
 *    - 자료 구조: A[k][i][r][c] (i <= logN)
 * 2. 주어진 테스트케이스에 대해, f(N) 을 구한다
 *    - ex) k=3, N=17 인 경우 A^17 = A[3][2^4] * A[3][2^0]
 *          f(17)은 A^17의 1행 1열에 위치한 원소
 *
 */


class P16467 {

    class Matrix(
        val arr: Array<LongArray> = emptyArray()
    )

    val MOD = 100_000_007L
    val MAX_LOG = 27
    val MAX_K = 10
    val A = Array(MAX_K+1) {
        Array(MAX_LOG+1) {
            Matrix()
        }
    }

    fun multiply(a: Matrix, b: Matrix): Matrix {
        val sz = a.arr.size
        return Matrix(
            Array(sz) { row ->
                LongArray(sz) { col ->
                    ((0..<sz).sumOf { a.arr[row][it] * b.arr[it][col] }) % MOD
                }
            }
        )
    }

    fun getUnitMatrix(sz: Int): Matrix {
        return Matrix(
            Array(sz) { row ->
                LongArray(sz) { col ->
                    if (row == col) 1L else 0L
                }
            }
        )
    }

    fun preprocess() {
        // k == 0 인 경우 2의 거듭제곱 스칼라
        var pow = 2L
        for (i in 0..MAX_LOG) {
            A[0][i] = Matrix(Array(1) { LongArray(1) { pow } })
            pow = (pow * pow) % MOD
        }

        // k > 0 인 경우 크기가 2X2 이상인 행렬
        for (k in 1..MAX_K) {
            val arr = Array(k+1) { LongArray(k+1) { 0L } }
            arr[0][0] = 1L
            arr[0][k] = 1L
            for (r in 1..k) {
                arr[r][r-1] = 1L
            }

            var mat = Matrix(arr)
            for (i in 0..MAX_LOG) {
                A[k][i] = mat
                mat = multiply(mat, mat)
            }
        }
    }

    fun solveTestCase(k: Int, n: Int): Int {
        val src = A[k]
        var ret = getUnitMatrix(k + 1)
        for (i in 0..MAX_LOG) {
            if (((1 shl i) and n) != 0) {
                ret = multiply(ret, src[i])
            }
        }
        return ret.arr[0][0].toInt()
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        val sb = StringBuilder()
        val T = readLine().toInt()
        repeat(T) {
            readLine().split(" ").map(String::toInt).let {
                val answer = solveTestCase(it[0], it[1])
                sb.append(answer).append('\n')
            }
        }
        print(sb)
    }
}

fun main() = with(P16467()) {
    preprocess()
    solve()
}