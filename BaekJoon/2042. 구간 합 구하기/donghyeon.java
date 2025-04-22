import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws IOException {
		int n, m = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] a = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = a[0];
		m = a[1];
		int[] arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		System.out.println(solution(arr, m));
	}

	public static long solution(int[] arr, int m) {

		int[] mCnt  = new int[m];      // 나머지 개수 카운트
		long[] accSum = new long[arr.length];
		int[] remain = new int[arr.length];

		accSum[0] = arr[0];
		remain[0] = (int)(accSum[0] % m);
		mCnt[remain[0]]++;

		for (int i = 1; i < arr.length; i++) {
			accSum[i] = accSum[i - 1] + arr[i];
			remain[i] = (int)(accSum[i] % m);
			mCnt[remain[i]]++;
		}

		long answer = mCnt[0];

		for (int i = 0; i < m; i++) {

			answer += Comb(mCnt[i]) ;

		}

		return answer; // long 그대로 반환
	}
	static long Comb(long n){
		if (n < 2)
			return 0;
		else
			return n * (n - 1) / 2;
	}

}

