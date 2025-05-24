import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws Exception {
        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[][] A = new char[n][];
        for (int i = 0; i < n; i++) {
            A[i] = br.readLine().toCharArray();
        }

        // 출발점 찾기
        int sr = -1;
        int sc = -1;
        for (int r = 0; r < n; r++) {
            if (sr != -1) break;
            for (int c = 0; c < m; c++) {
                if (A[r][c] == '2') {
                    sr = r;
                    sc = c;
                    break;
                }
            }
        }

        // 출발점에서 BFS 하며 최단거리 찾기
        int[] dr = new int[] { 0, 1, 0, -1 };
        int[] dc = new int[] { 1, 0, -1, 0 };
        ArrayDeque<Node> dq = new ArrayDeque<>();
        dq.offerLast(new Node(sr, sc));

        int dist = 1;
        while (!dq.isEmpty()) {
            int size = dq.size();
            while (size-- > 0) {
                Node cur = dq.pollFirst();
                for (int d = 0; d < 4; d++) {
                    int nr = cur.r + dr[d];
                    int nc = cur.c + dc[d];
                    if (nr < 0 || nr >= n || nc < 0 || nc >= m || A[nr][nc] == '1') {
                        continue;
                    }
                    int item = A[nr][nc] - '0';
                    if (3 <= item && item <= 5) {
                        System.out.println("TAK\n" + dist);
                        return;
                    }
                    dq.offerLast(new Node(nr, nc));
                    A[nr][nc] = '1';
                }
            }
            dist++;
        }
        System.out.println("NIE");
    }

    static class Node {
        int r, c;

        public Node(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
