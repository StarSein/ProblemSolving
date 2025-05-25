import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 *
 * H를 고려하지 않고, A만 고려했을 때 최적의 방법은 A의 오름차순으로 나무를 베는 것이다.
 * 이 방법은 항상 모든 나무를 자르므로, H가 한 번씩 결과에 반영된다.
 *
 * 이 방법에서 '자르는 순서'를 바꾸면 A 획득 총량이 감소한다.
 * 특정 나무를 2번 이상 자르면(= 안 자르는 나무가 발생하면)
 * A 획득 총량이 감소하고, H 획득 총량이 감소한다.
 *
 */

public class Main {

    public static void main(String[] args) throws Exception {
        // 입력을 받는다
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        long[] H = new long[n];
        for (int i = 0; i < n; i++) {
            H[i] = Long.parseLong(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        long[] A = new long[n];
        for (int i = 0; i < n; i++) {
            A[i] = Long.parseLong(st.nextToken());
        }

        // 배열 A를 오름차순 정렬한다
        Arrays.sort(A);

        // 정답을 계산한다
        long answer = Arrays.stream(H).sum() +
                IntStream.range(0, n)
                        .mapToLong(i -> i * A[i])
                        .sum();
        System.out.println(answer);
    }
}
