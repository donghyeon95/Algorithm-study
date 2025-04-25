import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		int k = inputs[0];
		int n = inputs[1];

		int[] wires = new int[k];
		for (int i = 0; i < k; i++) {
			wires[i] = Integer.parseInt(br.readLine());
		}

		int maxLen = Arrays.stream(wires).max().getAsInt();
		System.out.println(solution(n, wires, 1, maxLen, 0));
	}

	public static long solution(int n, int[] wires, long min, long max, long best) {
		if (min > max)
			return -1;

		long mid = (min + max) / 2;
		long result;
		if (possible(mid, n, wires)) {
			result = Math.max(solution(n, wires, mid + 1, max, mid), mid) ; // 저장
			return result;
		} else {
			return solution(n, wires, min, mid - 1, best); // 유지
		}
	}

	public static boolean possible(long len, int n, int[] wires) {
		if (len == 0)
			return false;

		long count = 0;
		for (int wire : wires) {
			count += wire / len;
		}
		return count >= n;
	}
}