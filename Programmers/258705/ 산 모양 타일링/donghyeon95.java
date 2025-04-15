class Solution {
	public int solution(int n, int[] tops) {

		// f(X)  <- x개 일때, 경우의 수
		// f(x) = 4*f(x-1) - f(x-2)
		// 삼각형 1개에서 나올 수 있는 경우의 수 4 or 3개 -  겹치는 삼각형으로 인해 없어지는 경우의 수

		if (n==1) return tops[0]==1? 4: 3;

		int x_2 = 1;
		int x_1 = tops[0]==1? 4: 3; // n= 1
		int answer = x_1;

		for (int i=1; i<n; i++) {
			int top = tops[i];
			answer = top==1? (4*x_1)-x_2: (3*x_1)-x_2;
			answer %= 10007;

			x_2 = x_1;

			if (answer < 0) answer+=10007;
			x_1 = answer;

		}

		return answer;

	}
}
