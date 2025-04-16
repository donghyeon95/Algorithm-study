class Solution {
    public int solution(int[][] info, int n, int m) {
        int items = info.length;
        boolean[][] dp = new boolean[n][m];
        dp[0][0] = true;

        for (int i = 0; i < items; i++) {
            boolean[][] next = new boolean[n][m];
            int aTrace = info[i][0];
            int bTrace = info[i][1];

            for (int a = 0; a < n; a++) {
                for (int b = 0; b < m; b++) {
                    if (dp[a][b]) {
                        if (a + aTrace < n) {
                            next[a + aTrace][b] = true;
                        }
                        if (b + bTrace < m) {
                            next[a][b + bTrace] = true;
                        }
                    }
                }
            }
            dp = next;
        }

        int answer = Integer.MAX_VALUE;
        for (int a = 0; a < n; a++) {
            for (int b = 0; b < m; b++) {
                if (dp[a][b]) {
                    answer = Math.min(answer, a);
                }
            }
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}
