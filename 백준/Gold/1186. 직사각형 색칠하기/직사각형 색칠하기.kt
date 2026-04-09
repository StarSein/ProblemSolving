import kotlin.math.max

/**
 *
 * 1. 각 직사각형마다 가려지지 않는 영역의 넓이 S를 구한다
 *  - 좌표값의 범위가 커서 좌표평면을 이용할 수는 없다
 *  - 포함 배제의 원리를 적용할 경우 최악의 경우 시간 복잡도가 2^N 이다
 *  - 분할 정복 (4분할)을 통해 구한다
 *    겹쳐진 영역이 존재한다면 4분할, 모든 영역이 겹쳐질 경우에 해당 직사각형의 넓이 반환, 반환받은 넓이 합산
 * 2. S가 큰 것부터, id값이 작은 것부터 K개를 출력한다 (그리디)
 *
 */


class P1186 {

    class Rect(
        val sx: Int,
        val sy: Int,
        val ex: Int,
        val ey: Int
    )

    fun Rect.getArea(): Int {
        return (ex - sx) * (ey - sy)
    }

    fun Rect.intersect(o: Rect): Int {
        if (o.sx < ex && o.sy < ey && sx < o.ex && sy < o.ey) {
            val xArr = intArrayOf(sx, ex, o.sx, o.ex).sorted()
            val yArr = intArrayOf(sy, ey, o.sy, o.ey).sorted()
            return (xArr[2] - xArr[1]) * (yArr[2] - yArr[1])
        } else {
            return 0
        }
    }

    fun Rect.getNetArea(idx: Int, rects: Array<Rect>): Int {
        val area = getArea()
        if (area == 0) return 0

        var maxIntersect = 0
        for (i in idx..rects.lastIndex) {
            maxIntersect = max(maxIntersect, intersect(rects[i]))
        }
        if (maxIntersect == 0) return area
        if (maxIntersect == area) return 0

        var ret = 0
        val mx = sx + ex shr 1
        val my = sy + ey shr 1
        ret += Rect(sx, sy, mx, my).getNetArea(idx, rects)
        ret += Rect(sx, my, mx, ey).getNetArea(idx, rects)
        ret += Rect(mx, sy, ex, my).getNetArea(idx, rects)
        ret += Rect(mx, my, ex, ey).getNetArea(idx, rects)
        return ret
    }

    fun solve() = with(System.`in`.bufferedReader()) {
        // 입력
        val (n, k) = readLine().split(" ").map { it.toInt() }
        val rects = Array(n) {
            val (sx, sy, ex, ey) = readLine().split(" ").map { it.toInt() }
            Rect(sx, sy, ex, ey)
        }

        // 풀이
        val netAreas = Array<Pair<Int, Int>>(n) {
            it + 1 to rects[it].getNetArea(it + 1, rects)
        }.sortedWith(compareBy({ -it.second }, { it.first }))

        // 출력
        val answer = netAreas.slice(0..<k)
            .map { it.first }
            .sorted()
            .joinToString(" ")
        println(answer)
    }
}

fun main() = with(P1186()) {
    solve()
}