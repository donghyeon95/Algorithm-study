public class Solution {
    public int solution(int[] players, int m, int k) {
        int totalExpansions = 0;
        int[] added = new int[24];

        for (int i = 0; i < 24; i++) {
            int active = 0;
            int start = Math.max(0, i - k + 1);

            for (int j = start; j <= i; j++) {
                active += added[j];
            }

            int required = players[i] / m;

            if (active < required) {
                int need = required - active;
                added[i] = need;
                totalExpansions += need;
            }
        }

        return totalExpansions;
    }
}
