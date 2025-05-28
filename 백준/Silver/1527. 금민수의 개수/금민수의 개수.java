import java.util.Scanner;

/**
 *
 * 조건을 만족하는 금민수의 개수는 최대 2^10 개이므로,
 * 완전 탐색으로 충분하다
 *
 */

public class Main {

    public static void main(String[] args) throws Exception {
        // 입력을 받는다
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();

        // 10^9 이하의 모든 금민수 중 'A 이상 B 이하' 조건을 충족하는 것의 개수(정답)를 구한다
        int answer = recur(1, 0, A, B);

        // 정답을 출력한다
        System.out.println(answer);
    }

    static int recur(int pow, int sum, int a, int b) {
        int ret = (a <= sum && sum <= b) ? 1 : 0;
        if (pow == 1_000_000_000) {
            return ret;
        }
        ret += recur(10 * pow, sum + 4 * pow, a, b);
        ret += recur(10 * pow, sum + 7 * pow, a, b);
        return ret;
    }
}
