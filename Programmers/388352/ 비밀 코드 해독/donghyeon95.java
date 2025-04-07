import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Solution {
	public int solution(int n, int[][] q, int[] ans) {

		int answer = 0;
		// 1~n 개로 만들 수 있는 조합을 만들고
		for(List<Integer> l: makeCombination(n)) {
			// q를 만족하는 지 확인.
			boolean correct = true;
			for (int i=0; i<q.length; i++) {
				int[] question = q[i];
				int a = ans[i];

				if (count(l, question) != a) {
					correct=false;
					break;
				}
			}

			if (correct) answer++;
		}

		return answer;
	}

	public int count(List<Integer> a, int[] b) {
		int count = 0;
		for (int i=0; i<a.size(); i++) {
			for (int j=0; j<b.length; j++) {
				if (a.get(i) == b[j]) {
					count++;
					break;
				}
			}
		}

		return count;
	}


	public Queue<List<Integer>> makeCombination(int n)  {
		Queue<List<Integer>> results = new ArrayDeque<>();

		for (int i=1; i<n-3; i++) {
			List<Integer> list = new ArrayList<>();
			list.add(i);
			results.add(list);
		}

		for (int i=0; i<4; i++) {
			Queue<List<Integer>> r = new ArrayDeque<>();
			while (!results.isEmpty()) {
				List<Integer> list = results.poll();
				for (int j = list.get(list.size()-1) + 1; j < n+1; j++) {
					List<Integer> l = new ArrayList<>(list);
					l.add(j);
					r.add(l);
				}

			}
			results = r;
		}

		return results;
	}
}