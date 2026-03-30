import kotlin.math.max

class P30054 {

    class Guest(
        val schedule: Int,
        val arrival: Int,
        var entered: Boolean = false
    )

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val n = readLine().toInt()
        val guests = Array(n) { readLine().split(" ").map { it.toInt() } }
            .map { Guest(it[0], it[1]) }
            .sortedWith(compareBy({ it.arrival }, { it.schedule }))

        // 풀이
        val maxTime = 400_400
        val guestDq = ArrayDeque<Guest>(guests)
        val waitingDq = ArrayDeque<Guest>()
        val earlyBirds = Array<Guest?>(maxTime + 1) { null }
        var answer = 0
        for (time in 1..maxTime) {
            while (guestDq.isNotEmpty() && guestDq.first().arrival == time) {
                val guest = guestDq.removeFirst()
                if (guest.schedule >= guest.arrival) {
                    earlyBirds[guest.schedule] = guest
                }
                waitingDq.addLast(guest)
            }

            if (earlyBirds[time]?.entered ?: true) {
                // 현재 시간에 예약한 손님이 없거나, 예약했던 손님이 이미 입장한 경우
                while (waitingDq.isNotEmpty() && waitingDq.first().entered) {
                    waitingDq.removeFirst()
                }
                if (waitingDq.isNotEmpty()) {
                    waitingDq.removeFirst().let { guest ->
                        answer = max(answer, time - guest.arrival)
                        guest.entered = true
                    }
                }
            } else {
                // 현재 시간에 예약한 손님이 아직 입장하지 않은 경우
                earlyBirds[time]?.let { guest ->
                    guest.entered = true
                    answer = max(answer, time - guest.arrival)
                }
            }
        }
        
        // 출력
        println(answer)
    }
}


fun main() = with(P30054()) {
    solve()
}