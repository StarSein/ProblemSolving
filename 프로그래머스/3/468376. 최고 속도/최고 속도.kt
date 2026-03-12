/*

# 접근
1. 입력받는 도로 하나를 카메라를 기준으로 양분한다.
   카메라 1대, 도로 2개를 각각 서로 다른 노드로 간주한다.
2. 서로 다른 카메라가 좌표값이 같으면 그중 제한 속도가 더 큰 것만 남긴다.
2. 도시 또한 노드로 간주한다.
   좌표가 겹치는 것들끼리 연결되어 있는 것으로 간주한다.
3. 이를 바탕으로 가중치가 없는 무향 그래프를 만든다. (노드는 최대 3,100개) - O(N^2)
4. 노드 1에서 탐색을 시작하며, 발견되는 노드를 (노드 번호, 최고 속도) 형태로 우선순위 큐에 넣는다.
   우선순위 큐는 최고 속도의 내림차순으로 정렬되도록 한다. - O(NlogE)
5. 각 도시를 처음 방문할 때마다 최고 속도를 저장한다. (첫 방문할 때가 최고 속도임이 보장된다.)

# 수정
1. 도시와 도로만 노드로 간주한다. 카메라는 제외한다.
2. 카메라를 간선으로 간주한다. 노드 간 겹침 또한 간주한다.

# 보완
1. 카메라를 기준으로 분할된 도로 위에 다른 카메라가 존재할 수 있음을 반영한다. (4거리)

*/
import java.util.PriorityQueue
import kotlin.math.min

class Solution {

    enum class NodeType {
        CITY, ROAD, CAMERA
    }

    class Node(
        val type: NodeType,
        val id: Int,
        val x1: Int,
        val y1: Int,
        val x2: Int,
        val y2: Int,
        val edges: MutableList<Edge> = mutableListOf<Edge>()
    )
    
    class Edge(
        val node: Node,
        val limit: Int
    ) {
        operator fun component1() = node
        operator fun component2() = limit
    }

    class Item(
        val node: Node,
        val maxSpeed: Int
    ) {
        operator fun component1() = node
        operator fun component2() = maxSpeed
    }
    
    val inf = 100_000_000
    val cameras = hashMapOf<Pair<Int, Int>, Int>()
    val cams = mutableListOf<Node>()

    fun Node.isConnected(e: Node): Boolean {
        return (x1.toLong() - e.x2) * (x2.toLong() - e.x1) <= 0L &&
                (y1.toLong() - e.y2) * (y2.toLong() - e.y1) <= 0L
    }
    
    fun Node.getLimit(e: Node): Int {
        val xArr = arrayOf(x1, x2, e.x1, e.x2).sorted()
        val yArr = arrayOf(y1, y2, e.y1, e.y2).sorted()
        val mx = xArr[1]
        val my = yArr[1]
        return cameras.getOrDefault(mx to my, inf)
    }

    fun solution(city: Array<IntArray>, road: Array<IntArray>): IntArray {
        val nodes = mutableListOf<Node>()
        city.forEachIndexed { i, (x, y) ->
            nodes.add(Node(NodeType.CITY, i, x, y, x, y))
        }
        var id = city.size
        road.forEach { (x1, y1, x2, y2, limit) ->
            val mx = (x1 + x2) / 2
            val my = (y1 + y2) / 2
            cameras[mx to my] = min(cameras.getOrDefault(mx to my, inf), limit)
            cams.add(Node(NodeType.CAMERA, -1, mx, my, mx, my))
        }
        road.forEach { (x1, y1, x2, y2, limit) ->
            val node = Node(NodeType.ROAD, -1, x1, y1, x2, y2)
            var x = x1
            var y = y1
            cams.filter { cam -> node.isConnected(cam) }.sortedWith(compareBy({ it.x1 }, { it.y1 })).forEach { cam ->
                nodes.add(Node(NodeType.ROAD, id++, x, y, cam.x1, cam.y1))
                x = cam.x1
                y = cam.y1
            }
            nodes.add(Node(NodeType.ROAD, id++, x, y, x2, y2))
        }

        for (i in 1..nodes.size-1) {
            val nodeA = nodes[i]
            for (j in 0..i-1) {
                val nodeB = nodes[j]

                if (nodeA.isConnected(nodeB)) {
                    val limit = nodeA.getLimit(nodeB)
                    nodeA.edges.add(Edge(nodeB, limit))
                    nodeB.edges.add(Edge(nodeA, limit))
                }
            }
        }

        val maxSpeeds = IntArray(city.size) { 0 }
        val visited = BooleanArray(nodes.size) { false }
        val pq = PriorityQueue<Item> { o1, o2 -> o2.maxSpeed - o1.maxSpeed }
        
        pq.add(Item(nodes[0], inf))
        while (pq.isNotEmpty()) {
            var (node, maxSpeed) = pq.poll()
            if (visited[node.id]) {
                continue
            }
            visited[node.id] = true

            if (node.type == NodeType.CITY) {
                maxSpeeds[node.id] = maxSpeed
            }
            for ((next, limit) in node.edges) {
                if (visited[next.id]) {
                    continue
                }
                val nextSpeed = min(maxSpeed, limit)
                pq.offer(Item(next, nextSpeed))
            }
        }

        val answer = (1..city.size-1).map { if (maxSpeeds[it] == inf) 0 else maxSpeeds[it] }.toIntArray()
        return answer
    }
}