import java.util.*;

class Solution {
    public int solution(int n, int[][] q, int[] ans) {
        int[] count = new int[1];
        List<Integer> current = new ArrayList<>();
        generateCombination(n, 1, current, q, ans, count);
        return count[0];
    }

    private void generateCombination(int n, int start, List<Integer> current, int[][] q, int[] ans, int[] count) {
        if (current.size() == 5) {
            if (validate(current, q, ans)) {
                count[0]++;
            }
            return;
        }

        for (int i = start; i <= n; i++) {
            current.add(i);
            generateCombination(n, i + 1, current, q, ans, count);
            current.remove(current.size() - 1);
        }
    }

    private boolean validate(List<Integer> candidate, int[][] q, int[] ans) {
        for (int i = 0; i < q.length; i++) {
            int matches = 0;
            for (int num : q[i]) {
                if (candidate.contains(num)) {
                    matches++;
                }
            }
            if (matches != ans[i]) {
                return false;
            }
        }
        return true;
    }
}
