import P23290.Fish
import P23290.Smell

/**
 *
 * # 구현 내용
 * 1. 격자의 모든 물고기를 따로 저장해 놓는다.
 * 2. 모든 물고기가 한 칸 이동한다. (8방향 이동 가능)
 *    이동할 수 없는 조건: 상어 존재, 물고기의 냄새 존재, 격자의 범위 밖
 *    이동할 수 있을 때까지 반시계 방향으로 45도씩 회전
 * 3. 상어가 연속 3칸 이동한다. (4방향 이동 가능)
 *    이동 중에 같은 칸에 물고기가 있으면 그 물고기는 제외되고, 물고기 냄새를 남긴다.
 *    이동 방법 4^3 가지 중, 격자의 범위를 벗어나지 않으면서, 가장 많은 물고기를 제외시키는 방법으로 이동한다.
 *    이동 방법 선택의 우선 순위 (1순위: 제외시키는 물고기의 수, 2순위: 사전 순으로 앞서는 것 : 왼-위-오른-아래)
 * 4. 두 번 전 연습에서 생긴 물고기 냄새가 사라진다.
 * 5. 1에서 따로 저장해 놓은 물고기가 격자에 추가된다.
 *  * 물고기 수는 '항상' (어떤 연습 시점이든) 1,000,000 이하인 입력만 주어진다.
 *
 * # 자료 구조
 * - Fish: 행, 열, 방향, 개체 수
 * - Shark: 행, 열, 방향
 * - Smell: 행, 열, 생성 시점
 * - Fish 격자: Array<Array<Int>> (행, 열)에 해당하는 물고기의 개수
 * - Smell 격자: Array<Array<Int>> (행, 열)에 해당하는 물고기 냄새의 중첩 수
 * - 1, 2, 5: List<Fish> - 물고기 리스트 2개
 * - 3, 4: ArrayDeque<Smell> - 냄새 덱 (ArrayDeque.first의 createdTime이 2초 전이면 removeFirst 하면서 냄새를 지운다)
 *
 */

// 사전 정의
val size = 4
val fdCount = 8
val sdCount = 4
val fishGrid = Array(size) { Array(size) { 0 } }
val fishDirGrid = Array(size) { Array(size) { Array(fdCount) { 0 } } }
val smellGrid = Array(size) { Array(size) { 0 } }
val fdr = arrayOf(0, -1, -1, -1, 0, 1, 1, 1)
val fdc = arrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
val sdr = arrayOf(-1, 0, 1, 0)
val sdc = arrayOf(0, -1, 0, 1)
val liveFishes = mutableListOf<Fish>()
val smellDq = ArrayDeque<Smell>()


class P23290 {

    class Fish(
        val row: Int,
        val col: Int,
        val dir: Int,
        val count: Int
    )

    class Shark(
        var row: Int,
        var col: Int
    )

    class Smell(
        val createdTime: Int,
        val points: List<Pair<Int, Int>>
    )
    
    class Movement(
        val fishCount: Int,
        val dirs: Array<Int> // 3개의 dir
    )
    
    operator fun Movement.compareTo(e: Movement): Int {
        if (fishCount != e.fishCount) return e.fishCount - fishCount
        repeat(dirs.size) {
            if (dirs[it] != e.dirs[it]) return dirs[it] - e.dirs[it]
        }
        return -1
    }

    fun Movement.makeBestMovement(i: Int, dArr: Array<Int>, fishCount: Int, sr: Int, sc: Int): Movement {
        if (i == dArr.size) {
            return Movement(fishCount, dArr.copyOf())
        }
        var bestMovement = this
        for (d in 0..<sdCount) {
            dArr[i] = d
            val nr = sr + sdr[d]
            val nc = sc + sdc[d]
            if (nr in 0..<size && nc in 0..<size) {
                val fishCountAdded = fishGrid[nr][nc]
                fishGrid[nr][nc] = 0

                val movement = this.makeBestMovement(i + 1, dArr, fishCount + fishCountAdded, nr, nc)
                if (movement < bestMovement) {
                    bestMovement = movement
                }

                fishGrid[nr][nc] = fishCountAdded
            }
        }
        return bestMovement
    }

    fun solve() = with(System.`in`.bufferedReader()) {

        // 입력
        val (m, s) = readLine().split(" ").map(String::toInt)
        repeat(m) {
            val (fr, fc, fd) = readLine().split(" ").map { it.toInt() - 1 }
            liveFishes.add(Fish(fr, fc, fd, 1))
        }
        val shark = readLine().split(" ").map { it.toInt() - 1 }.let {
            Shark(it[0], it[1])
        }

        // 풀이
        // s번 반복하면서
        for (time in 0..<s) {
            // 작업 1
            // liveFishes에는 변경사항이 없도록 유지

            // 작업 2
            // liveFishes 물고기 이동 내용을 fishGrid, fishDirGrid에 반영
            liveFishes.forEach { fish ->
                var nd = fish.dir
                var movable = false
                for (i in 0..<fdCount) {
                    val nr = fish.row + fdr[nd]
                    val nc = fish.col + fdc[nd]
                    if (nr in 0..<size && 
                        nc in 0..<size && 
                        smellGrid[nr][nc] == 0 && 
                        !(nr == shark.row && nc == shark.col)) {
                        fishGrid[nr][nc] += fish.count
                        fishDirGrid[nr][nc][nd] += fish.count
                        movable = true
                        break
                    }
                    nd = (nd - 1 + fdCount) % fdCount
                }
                if (!movable) {
                    fishGrid[fish.row][fish.col] += fish.count
                    fishDirGrid[fish.row][fish.col][fish.dir] += fish.count
                }
            }

            // 작업 3
            // shark 최적 이동 선택
            val dArr = arrayOf(fdCount, fdCount, fdCount)
            val worstMovement = Movement(fishCount = -1, dArr.copyOf())
            val bestMovement = worstMovement.makeBestMovement(
                i = 0,
                dArr = dArr,
                fishCount = 0,
                sr = shark.row,
                sc = shark.col
            )

            // shark 최적 이동 실행 -> 제거된 것들은 smellGrid에 반영 및 smellDq에 추가
            val newSmellList = mutableListOf<Pair<Int, Int>>()
            bestMovement.dirs.forEach { d ->
                shark.row += sdr[d]
                shark.col += sdc[d]
                if (fishGrid[shark.row][shark.col] != 0) {
                    fishGrid[shark.row][shark.col] = 0
                    fishDirGrid[shark.row][shark.col].fill(0)

                    smellGrid[shark.row][shark.col]++
                    newSmellList.add(shark.row to shark.col)
                }
            }
            smellDq.addLast(Smell(createdTime = time, points = newSmellList.toList()))

            // 작업 4
            // smellDq.first.createdTime 확인 및 제거 처리
            if (smellDq.isNotEmpty() && smellDq.first().createdTime + 2 == time) {
                smellDq.removeFirst().points.forEach { (r, c) ->
                    smellGrid[r][c]--
                }
            }

            // 작업 5
            // liveFishes를 fishDirGrid에 반영
            liveFishes.forEach { fish ->
                fishDirGrid[fish.row][fish.col][fish.dir] += fish.count
            }

            // liveFishes 초기화
            liveFishes.clear()

            // fishDirGrid를 liveFishes에 반영
            repeat(size) { r ->
                repeat(size) { c ->
                    repeat(fdCount) { d ->
                        if (fishDirGrid[r][c][d] != 0) {
                            liveFishes.add(Fish(r, c, d, fishDirGrid[r][c][d]))
                        }
                    }
                }
            }

            // fishGrid, fishDirGrid 초기화
            repeat(size) { r ->
                fishGrid[r].fill(0)
                repeat(size) { c ->
                    fishDirGrid[r][c].fill(0)
                }
            }
        }

        // 출력
        val answer = liveFishes.sumOf { fish -> fish.count }
        println(answer)
    }

}

fun main() = with(P23290()) {
    solve()
}