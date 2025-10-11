import java.util.Scanner
import kotlin.math.max

/**
 *
 * # 전제
 * 1. 사이클이 반복되는 경로 또한 가능하지만, 2회 이상 반복되는 사이클은 본전이거나 손해인 선택이다.
 *
 * # 판단
 * 1. 모든 (방 번호, 금액) 조합에 대해 방문 처리를 하면 TLE가 예상된다.
 * 2. 방마다 해당 방에 들어간 시점의 최대 소지금을 유지하도록 하는 메모이제이션 방식은?
 *    완전 그래프이고 1~500원짜리 레프리콘이 모두 있는 경우 TLE가 날 수 있다.
 * 3. 다른 방법이 안 떠오른다. 채점 데이터가 널널하길 바라자.
 *
 * # 접근
 * 1. BFS - 시간 O(N^2), 공간 O(N^2)
 * 단, 방마다 해당 방에 들어간 시점의 최대 소지금을 유지하고 있어야 한다.
 * -> 중복 방문을 줄이기 위함.
 *
 */

private class P2310 {

    class Room(
        val type: Char,
        val money: Int,
        val nextRoomIndices: IntArray
    ) {
        companion object {
            val DUMMY_ROOM = Room(type = 'E', money = 0, nextRoomIndices = intArrayOf())
        }
    }

    data class Player(
        val roomIdx: Int,
        val cash: Int
    )

    private val WS = Regex("\\s+")

    var n = 0
    var rooms: List<Room> = emptyList()
    var cache: IntArray = intArrayOf()
    val tok: Iterator<String> = System.`in`.bufferedReader()
        .lineSequence()
        .flatMap { line ->
            if (line.isBlank()) emptySequence()
            else line.trim().splitToSequence(WS)
        }
        .iterator()
    val outputBuilder = StringBuilder()

    fun loop() {
        while (readInput()) {
            solve()
        }
    }

    fun readInput(): Boolean {
        n = tok.next().toInt()
        if (n == 0) {
            return false
        }

        rooms = ArrayList(n+1)
        rooms += Room.DUMMY_ROOM
        repeat(n) {
            val type = tok.next().first()
            val money = tok.next().toInt()
            val nextRoomIndices: ArrayList<Int> = arrayListOf()
            while (true) {
                val roomIdx = tok.next().toInt()
                if (roomIdx == 0) break
                nextRoomIndices.add(roomIdx)
            }

            rooms += Room(
                type = type,
                money = money,
                nextRoomIndices = nextRoomIndices.toIntArray()
            )
        }
        return true
    }

    fun solve() {
        cache = IntArray(n+1) { -1 }
        val dq = ArrayDeque<Player>()
        dq.addLast(Player(roomIdx = 1, cash = 0))
        while (dq.isNotEmpty()) {
            var player = dq.removeFirst()
            val room = rooms[player.roomIdx]

            // 레프리콘 방이면 p.cash = max(p.cash, r.money) 가 된다
            // 트롤 방이면 p.cash = p.cash - r.money 가 되고,
            // p.cash < 0 이 되면 탐색에서 배제한다
            when (room.type) {
                'L' -> {
                    player = player.copy(
                        cash = max(player.cash, room.money)
                    )
                }
                'T' -> {
                    player = player.copy(
                        cash = player.cash - room.money
                    )
                    if (player.cash < 0) {
                        continue
                    }
                }
                else -> { }
            }

            // n번 방에 도착하면 'Yes'를 정답에 할당하고 return 한다
            if (player.roomIdx == n) {
                outputBuilder.append("Yes\n")
                return
            }

            // 다음 방으로 이동한다
            room.nextRoomIndices.forEach { nextRoomIdx ->
                if (player.cash > cache[nextRoomIdx]) {
                    dq.addLast(player.copy(roomIdx = nextRoomIdx))
                    cache[nextRoomIdx] = player.cash
                }
            }

        }
        // 루프가 종료되면 'No'를 정답에 할당한다
        outputBuilder.append("No\n")
    }

    fun writeOutput() {
        print(outputBuilder.toString())
    }
}

fun main() = with(P2310()) {
    loop()
    writeOutput()
}