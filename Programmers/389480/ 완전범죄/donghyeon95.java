// class Solution {
// 	private int limitA;
// 	private int limitB;
// 	private int infoLen;
//
// 	public int solution(int[][] info, int n, int m) {
// 		// A와 B 도둑이 안걸리게 최대한 많은 물건을 훔쳐라
// 		limitA = n;
// 		limitB = m;
// 		infoLen = info.length;
//
// 		return steal(info, 0, 0, 0);
//
// 	}
//
// 	/**
// 	 * @Param info 현재 물건
// 	 * @Param a a도둑 현재 합계
// 	 * @Param b b도둑 현재 합계
// 	 */
// 	public int steal(int[][] info, int a, int b, int index) {
// 		// 잡히는 경우 return -1;
// 		if (a>=limitA || b>=limitB) return -1;
// 		if (index >= infoLen) return a;
//
//
// 		int[] i = info[index];
// 		int stealResult = 0;
// 		int unStealResult = 0;
//
// 		// 이걸 A가 훔쳤을 때,
// 		stealResult = steal(info, a+i[0], b, index+1);
//
// 		// B가 훔쳤을 떄,
// 		unStealResult = steal(info, a, b+i[1], index+1);
//
// 		if (stealResult==-1 && unStealResult != -1) return unStealResult;
// 		if (stealResult!=-1 && unStealResult == -1) return stealResult;
// 		return Math.min(stealResult, unStealResult);
// 	}
// }


// 변환 해보면서 느낀점
// 함수의 상태를 배열로 저장한다.
// 배열의 값은 함수의 return 값
// 배열은 필수 파라미터 값


class Solution {
	public int solution(int[][] info, int n, int m) {
		int[][][] dp = new int[info.length][120][120];

		// init
		for (int i=0; i< dp.length; i++) {
			for (int j=0; j<120; j++) {
				for (int v=0; v<120; v++) {
					dp[i][j][v] = Integer.MAX_VALUE;
				}
			}
		}

		int[] in = info[0];
		if (in[0] < n)
			dp[0][in[0]][0] = in[0];

		if (in[1]<m)
			dp[0][0][in[1]] = 0;

		for (int i=1; i< dp.length; i++) {
			int[] newIn = info[i];

			for (int a=0; a<n; a++) {
				for (int b=0; b<m; b++) {
					if (dp[i - 1][a][b] != Integer.MAX_VALUE) {
						// A 선택
						if (a + newIn[0] < n) {
							dp[i][a + newIn[0]][b] = Math.min(dp[i - 1][a][b] + newIn[0], dp[i][a + newIn[0]][b]);
						}

						// B 선택
						if (b + newIn[1] < m) {
							dp[i][a][b + newIn[1]] = Math.min(dp[i - 1][a][b], dp[i][a][b + newIn[1]]);
						}
					}
				}
			}

		}
		int answer = Integer.MAX_VALUE;

		for (int a=0; a<n; a++) {
			for (int b=0; b<m; b++) {
				answer = Math.min(dp[info.length - 1][a][b], answer);
			}
		}

		return answer==Integer.MAX_VALUE? -1 : answer;
	}
}