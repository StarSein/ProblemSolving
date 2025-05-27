import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws Exception {
        // 입력을 받는다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Point[] checkpoints = new Point[N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            checkpoints[i] = new Point(
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())
            );
        }

        // 모든 체크포인트를 지날 때의 총 거리를 구한다
        int totalDist = 0;
        for (int i = 1; i < N; i++) {
            totalDist += distance(checkpoints[i - 1], checkpoints[i]);
        }

        // [2, N-1]의 체크포인트 하나씩 건너뛰어보면서 최소 거리를 구한다
        int minDist = totalDist;
        for (int i = 1; i < N - 1; i++) {
            int dist = totalDist - distance(checkpoints[i - 1], checkpoints[i]) - distance(checkpoints[i], checkpoints[i + 1])
                    + distance(checkpoints[i - 1], checkpoints[i + 1]);
            minDist = Math.min(minDist, dist);
        }

        // 최소 거리를 출력한다
        System.out.println(minDist);
    }

    static int distance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
