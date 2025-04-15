import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

class Solution {
    private static int[] answer = {};
    private static int max = Integer.MIN_VALUE;

    public int[] solution(int[][] dice) {

        combination(dice.length / 2, 0, dice, new java.util.ArrayList<>(), 0);

        return answer;
    }

    // 조합의 수
    private void combination(int maxDepth, int depth, int[][] diceList, List<Integer> stack, int peekIdx) {
        if (depth >= maxDepth) {
            // A, B 다이스 조합
            List<Integer> aList = stack;
            List<Integer> bList = new java.util.ArrayList<>();

            for (int i = 0; i < diceList.length; i++) {
                if (!aList.contains(i)) {
                    bList.add(i);
                }
            }

            // 각자 모든 합 계산(B는 오름차순 정렬)
            List<Integer> aSums = new java.util.ArrayList<>();
            List<Integer> bSums = new java.util.ArrayList<>();

            findAllSum(
                    IntStream.range(0, diceList.length)
                            .filter(aList::contains)
                            .mapToObj(idx -> diceList[idx])
                            .toArray(int[][]::new),
                    0, 0, aSums);
            findAllSum(
                    IntStream.range(0, diceList.length)
                            .filter(bList::contains)
                            .mapToObj(idx -> diceList[idx])
                            .toArray(int[][]::new),
                    0, 0, bSums);

            Collections.sort(bSums);

            // 이분탐색
            int aWin = 0;
            for (int i = 0; i < aSums.size(); i++) {
                int target = aSums.get(i);
                int temp = upperBound(bSums, target);
                aWin += temp;
            }

            if (aWin > max) {
                max = aWin;
                answer = new int[stack.size()];
                for (int j = 0; j < stack.size(); j++) {
                    answer[j] = stack.get(j) + 1;
                }
            }

            if (aWin > max) {
                max = aWin;
                answer = new int[stack.size()];
                for (int j = 0; j < stack.size(); j++) {
                    answer[j] = stack.get(j) + 1;
                }
            }

            return ;
        }

        for (int i = peekIdx; i < diceList.length; i++) {
            stack.add(i);
            combination(maxDepth, depth + 1, diceList, stack, i + 1);
            stack.remove(stack.size() - 1);
        }
    }

    private void findAllSum(int[][] diceList, int depth, int sum, List<Integer> results) {
        if (depth >= diceList.length) {
            results.add(sum);
            return ;
        }

        for (int i = 0; i < diceList[0].length; i++) {
            sum += diceList[depth][i];
            findAllSum(diceList, depth + 1, sum, results);
            sum -= diceList[depth][i];
        }
    }

    // target보다 작은 값의 최대 인덱스
    private int upperBound(List<Integer> list, int target) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (list.get(mid) >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }
}