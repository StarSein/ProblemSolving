import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws Exception {
        // 입력을 받으면서 모눈종이에 직사각형을 칠한다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        boolean[][] board = new boolean[M][N];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int sx = Integer.parseInt(st.nextToken());
            int sy = Integer.parseInt(st.nextToken());
            int ex = Math.min(N, Integer.parseInt(st.nextToken()));
            int ey = Math.min(M, Integer.parseInt(st.nextToken()));
            for (int y = sy; y < ey; y++) {
                for (int x = sx; x < ex; x++) {
                    board[y][x] = true;
                }
            }
        }

        // 모눈종이의 빈 공간을 찾게 되면 거기서 BFS를 통해 넓이를 구한다
        // BFS를 하는 도중에 만나는 공간을 채운다
        int[] dx = { 0, 1, 0, -1};
        int[] dy = { 1, 0, -1, 0};
        ArrayList<Integer> areaList = new ArrayList<>();
        ArrayDeque<Node> dq = new ArrayDeque<>();
        for (int y = 0; y < M; y++) {
            for (int x = 0; x < N; x++) {
                if (board[y][x]) continue;
                dq.clear();

                dq.offerLast(new Node(x, y));
                board[y][x] = true;
                int currentArea = 1;
                while (!dq.isEmpty()) {
                    Node cur = dq.pollFirst();
                    for (int d = 0; d < 4; d++) {
                        int nx = cur.x + dx[d];
                        int ny = cur.y + dy[d];
                        if (nx < 0 || nx >= N || ny < 0 || ny >= M || board[ny][nx]) continue;
                        dq.offerLast(new Node(nx, ny));
                        board[ny][nx] = true;
                        currentArea++;
                    }
                }

                areaList.add(currentArea);
            }
        }

        // 영역의 개수를 출력한다
        System.out.println(areaList.size());
        // 영역의 넓이를 오름차순으로 출력한다
        areaList.stream().sorted().forEach(it -> System.out.print(it + " "));
    }

    static class Node {
        int x, y;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
