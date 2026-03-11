/*

A: 한 번의 이동으로 어떤 앱은 10번까지 움직일 수도 있다고 가정하자.
B: 최대 100개의 앱 존재 가능
C: 앱 하나당 영향 받는 앱 조회를 위해 100번의 탐색 가능
D: 이동 명령 횟수 1000회
naive하게 A*B*C*D 계산하면 작업의 반복 횟수가 10^8 이다.
단, B 및 C가 커질수록 A가 작아지는 상쇄 관계가 있음에 주목하면, 시간 제약을 만족할 수 있으리라 추측된다.
꼬리에 꼬리를 물고 연쇄 반응을 쫓아가는 식으로 구현해야 한다.

격자에 그대로 이동 상태를 적용하는 것은 구현이 복잡하므로 각 앱의 좌표값을 기반으로 
모든 이동 명령을 처리하고,
마지막에 모든 앱을 격자에 표현하자.

각 앱별 이동 함수 
1) 직접 이동의 경우에는 1만큼 이동한다.
2) 그렇지 않을 경우에는 현재 겹치는 앱을 모두 고려했을 때 이동해야 하는 거리 t를 구한다.
3) 이동했을 때 격자 밖으로 이동하게 될 경우 반대편 이동을 위해 추가 이동을 한다.
4) 이동 후 영향을 받게 되는 앱을 찾아서 큐에 넣는다.
이와 같이 FIFO 방식으로 lazy하게 각 앱의 이동을 처리하면 중복 이동을 피할 수 있다.

아니다. 한 번 이동 거리가 1 초과일 경우, 구현이 상당히 복잡해진다.
항상 1만큼만 이동하게끔 하고, 경계면에서 잘림 현상이 사라질 때까지 1만큼 이동을 반복한다.

이동 함수
1) 이동 대상이 있을 때, 이동 방향으로 인접한 앱들을 연쇄적으로 모두 이동 대상으로 삼는다.
2) 모든 이동 대상 앱들의 좌표값을 이동 방향으로 1씩 증가시킨다.
3) 경계면에 잘린 앱이 있을 경우, 그 앱을 이동 대상으로 삼는다.
4) 경계면에 잘린 앱이 없어질 때까지, 1~3)을 반복한다.

*/
import kotlin.math.*


class Solution {
    
    var n = 0
    var m = 0
    val dr = arrayOf(0, 0, 1, 0, -1) 
    val dc = arrayOf(0, 1, 0, -1, 0)
    
    class App(
        val id: Int,
        var sr: Int,
        var sc: Int,
        var er: Int,
        var ec: Int,
        var isMoving: Boolean = false
    )
    
    fun App.isPushing(x: App, arrow: Int): Boolean {
        return when (arrow) {
            1 -> {
                !(x.er < sr || er < x.sr) && (ec + 1 + m) % m == x.sc
            }
            2 -> {
                !(x.ec < sc || ec < x.sc) && (er + 1 + n) % n == x.sr
            }
            3 -> {
                !(x.er < sr || er < x.sr) && sc == (x.ec + 1 + m) % m
            }
            else -> {
                !(x.ec < sc || ec < x.sc) && sr == (x.er + 1 + n) % n
            }
        }
    }
    
    fun App.move(arrow: Int) {
        sr = (sr + dr[arrow] + n) % n
        sc = (sc + dc[arrow] + m) % m
        er = (er + dr[arrow] + n) % n
        ec = (ec + dc[arrow] + m) % m
    }
    
    fun App.isCut(): Boolean {
        return sr > er || sc > ec
    }
    
    fun App.printOnBoard(board: Array<IntArray>) {
        (sr..er).forEach { r ->
            (sc..ec).forEach { c ->
                board[r][c] = id
            }
        }
    }
    
    fun solution(board: Array<IntArray>, commands: Array<IntArray>): Array<IntArray> {
        n = board.size
        m = board[0].size
        
        // 너비 우선 탐색을 통해 격자로부터 앱 정보를 저장한다.
        val appList = mutableListOf<App>()
        repeat(n) { r ->
            repeat(m) { c ->
                val id = board[r][c]
                var er = r
                var ec = c
                if (id != 0) {
                    val dq = ArrayDeque<Pair<Int, Int>>()
                    dq.addLast(r to c)
                    board[r][c] = 0

                    while (dq.isNotEmpty()) {
                        val (cr, cc) = dq.removeLast()
                        for (d in 1..4) {
                            val nr = cr + dr[d]
                            val nc = cc + dc[d]
                            if (nr in 0..n-1 && nc in 0..m-1 && board[nr][nc] == id) {
                                dq.addLast(nr to nc)
                                board[nr][nc] = 0
                                er = max(er, nr)
                                ec = max(ec, nc)
                            }
                        }
                    }
                    appList.add(App(id, r, c, er, ec))
                }
            }
        }
        val apps = appList.sortedBy { app -> app.id }
        
        // 모든 이동을 하나씩 처리한다.
        commands.forEach { (sid, arrow) ->
            val starterApp = apps[sid-1]
            val dq = ArrayDeque<App>()
            dq.addLast(starterApp)
            
            while (dq.isNotEmpty()) {
                
                apps.forEach { app -> app.isMoving = false }
                
                val movingApps = mutableListOf<App>()
                
                while (dq.isNotEmpty()) {
                    val curApp = dq.removeFirst()
                    
                    if (curApp.isMoving) continue
                    curApp.isMoving = true
                    
                    movingApps.add(curApp)
                    
                    apps.forEach { nextApp ->
                        if (curApp.isPushing(nextApp, arrow)) {
                            dq.addLast(nextApp)
                        }
                    }
                }
                
                movingApps.forEach { app -> app.move(arrow) }
                movingApps.filter { app -> app.isCut() }
                    .forEach { app -> dq.addLast(app) }
            }  
        }
    
        val answer = Array(n) { IntArray(m) { 0 } }
        apps.forEach { app -> app.printOnBoard(answer) }
        return answer
    }
}