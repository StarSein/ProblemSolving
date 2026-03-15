class P23796 {

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val board = Array(8) { readLine().split(" ").map(String::toLong).toLongArray() }
        val cmd = readLine()

        // 풀이
        val merged = Array(8) { BooleanArray(8) }
        when (cmd) {
            "U" -> {
                for (r in 1..7) {
                    for (c in 0..7) {
                        if (board[r][c] == 0L) continue
                        if (board[r-1][c] != 0L && board[r-1][c] != board[r][c]) continue
                        var nr = r - 1
                        while (nr >= 0 && board[nr][c] == 0L) {
                            nr--
                        }
                        if (nr == -1 || board[nr][c] != board[r][c] || merged[nr][c]) {
                            board[nr+1][c] = board[r][c]
                        } else {
                            board[nr][c] *= 2L
                            merged[nr][c] = true
                        }
                        board[r][c] = 0
                    }
                }
            }
            "D" -> {
                for (r in 6 downTo 0) {
                    for (c in 0..7) {
                        if (board[r][c] == 0L) continue
                        if (board[r+1][c] != 0L && board[r+1][c] != board[r][c]) continue
                        var nr = r + 1
                        while (nr <= 7 && board[nr][c] == 0L) {
                            nr++
                        }
                        if (nr == 8 || board[nr][c] != board[r][c] || merged[nr][c]) {
                            board[nr-1][c] = board[r][c]
                        } else {
                            board[nr][c] *= 2L
                            merged[nr][c] = true
                        }
                        board[r][c] = 0
                    }
                }
            }
            "L" -> {
                for (c in 1..7) {
                    for (r in 0..7) {
                        if (board[r][c] == 0L) continue
                        if (board[r][c-1] != 0L && board[r][c-1] != board[r][c]) continue
                        var nc = c - 1
                        while (nc >= 0 && board[r][nc] == 0L) {
                            nc--
                        }
                        if (nc == -1 || board[r][nc] != board[r][c] || merged[r][nc]) {
                            board[r][nc+1] = board[r][c]
                        } else {
                            board[r][nc] *= 2L
                            merged[r][nc] = true
                        }
                        board[r][c] = 0
                    }
                }
            }
            "R" -> {
                for (c in 6 downTo 0) {
                    for (r in 0..7) {
                        if (board[r][c] == 0L) continue
                        if (board[r][c+1] != 0L && board[r][c+1] != board[r][c]) continue
                        var nc = c + 1
                        while (nc <= 7 && board[r][nc] == 0L) {
                            nc++
                        }
                        if (nc == 8 || board[r][nc] != board[r][c] || merged[r][nc]) {
                            board[r][nc-1] = board[r][c]
                        } else {
                            board[r][nc] *= 2L
                            merged[r][nc] = true
                        }
                        board[r][c] = 0
                    }
                }
            }
        }

        // 출력
        repeat(8) { r ->
            println(board[r].joinToString(" "))
        }
    }
}


fun main() = with(P23796()) {
    solve()
}