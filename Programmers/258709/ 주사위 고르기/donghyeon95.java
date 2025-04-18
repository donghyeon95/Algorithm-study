import java.util.*;

class Solution {
	public int[] solution(int[][] dice) {
		int n = dice.length;
		ArrayList<ArrayList<Integer>> choosedDices = getChoosedDices(n);
		int maxWin = -1;
		ArrayList<Integer> bestSet = null;

		for (ArrayList<Integer> aSet : choosedDices) {
			ArrayList<Integer> bSet = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				if (!aSet.contains(i)) bSet.add(i);
			}

			ArrayList<Integer> aSums = calcSum(dice, aSet);
			ArrayList<Integer> bSums = calcSum(dice, bSet);

			int winCount = getLargeCnt(aSums, bSums);

			if (winCount > maxWin) {
				maxWin = winCount;
				bestSet = aSet;
			}
		}

		int[] answer = new int[n / 2];
		for (int i = 0; i < answer.length; i++) {
			answer[i] = bestSet.get(i) + 1;
		}
		return answer;
	}

	// A가 n/2개를 뽑는 경우 (조합)
	public ArrayList<ArrayList<Integer>> getChoosedDices(int n) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<>();
		dfs(0, n, n / 2, new ArrayList<>(), result);
		return result;
	}

	// 조합 구하기
	private void dfs(int start, int n, int depth, ArrayList<Integer> path, ArrayList<ArrayList<Integer>> result) {
		if (path.size() == depth) {
			result.add(new ArrayList<>(path));
			return;
		}
		for (int i = start; i < n; i++) {
			path.add(i);
			dfs(i + 1, n, depth, path, result);
			path.remove(path.size() - 1);
		}
	}

	// 선택된 주사위 조합에서 만들 수 있는 모든 합 계산
	public ArrayList<Integer> calcSum(int[][] dice, ArrayList<Integer> indices) {
		ArrayList<Integer> sumList = new ArrayList<>();
		dfsSum(0, 0, dice, indices, sumList);
		return sumList;
	}

	private void dfsSum(int depth, int sum, int[][] dice, ArrayList<Integer> indices, ArrayList<Integer> result) {
		if (depth == indices.size()) {
			result.add(sum);
			return;
		}
		int diceIdx = indices.get(depth);
		for (int i = 0; i < 6; i++) {
			dfsSum(depth + 1, sum + dice[diceIdx][i], dice, indices, result);
		}
	}

	// A의 합이 B의 합보다 큰 경우의 수 계산
	public int getLargeCnt(ArrayList<Integer> a, ArrayList<Integer> b) {
		int count = 0;
		Collections.sort(b);
		for (int valA : a) {
			int idx = upperBound(b, valA); // valB < valA 인 것 개수
			count += idx;
		}
		return count;
	}

	// upperBound: target보다 작은 값들의 개수
	private int upperBound(ArrayList<Integer> list, int target) {
		int left = 0, right = list.size();
		while (left < right) {
			int mid = (left + right) / 2;
			if (list.get(mid) < target) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}
		return left;
	}
}
