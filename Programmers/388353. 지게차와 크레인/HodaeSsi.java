import java.util.Queue;

class Solution {
    private static final int[][] directions = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    // 풀이 키워드 : 완전 탐색, BFS
    // 풀이 내용
    // 요청 마다 배열 전체를 BFS와 함께 완전 탐색
    // 시간 복잡도
    // O(R * N^2 * M^2) : R: 요청 수, N: 행 길이, M: 열 길이
    // 공간 복잡도
    // O(N * M) : N: 행 길이, M: 열 길이
    public int solution(String[] storage, String[] requests) {
        int answer = 0;

        String[] storageWithMargin = new String[storage.length + 2];
        storageWithMargin[0] = "0".repeat(storage[0].length() + 2);
        for (int i = 0; i < storage.length; i++) {
            storageWithMargin[i + 1] = "0" + storage[i] + "0";
        }
        storageWithMargin[storage.length + 1] = "0".repeat(storage[0].length() + 2);

        for (String request: requests) {
            if (request.length() == 1) { // 지게차
                for (int i = 0; i < storageWithMargin.length; i++) {
                    StringBuilder sb = new StringBuilder(storageWithMargin[i]);
                    for (int j = 0; j < sb.length(); j++) {
                        if (sb.charAt(j) == request.charAt(0)) {
                            Queue<int[]> queue = new java.util.LinkedList<>(); // i, j, value
                            queue.add(new int[]{i, j, sb.charAt(j)});
                            boolean[][] visited = new boolean[storageWithMargin.length][storageWithMargin[i].length()];
                            visited[i][j] = true;
                            boolean found = false;

                            while (!queue.isEmpty()) {
                                int[] now = queue.poll();

                                for (int[] dir: directions) {
                                    int ni = now[0] + dir[0];
                                    int nj = now[1] + dir[1];

                                    if (ni < 0 || ni >= storageWithMargin.length || nj < 0 || nj >= storageWithMargin[i].length()) {
                                        continue;
                                    }

                                    char nextChar = storageWithMargin[ni].charAt(nj);
                                    if (nextChar == '0') {
                                        found = true;
                                        break;
                                    } else if (nextChar == '2' && !visited[ni][nj]) {
                                        queue.add(new int[]{ni, nj, nextChar});
                                        visited[ni][nj] = true;
                                    }
                                }
                            }

                            if (found) {
                                sb.setCharAt(j, '1');
                            }
                        }
                    }
                    storageWithMargin[i] = sb.toString();
                }

                for (int i = 0; i < storageWithMargin.length; i++) {
                    StringBuilder sb = new StringBuilder(storageWithMargin[i]);
                    for (int j = 0; j < sb.length(); j++) {
                        if (sb.charAt(j) == '1') {
                            sb.setCharAt(j, '2');
                        }
                    }
                    storageWithMargin[i] = sb.toString();
                }
            } else { // 크레인
                for (int i = 0; i < storageWithMargin.length; i++) {
                    StringBuilder sb = new StringBuilder(storageWithMargin[i]);
                    for (int j = 0; j < sb.length(); j++) {
                        if (sb.charAt(j) == request.charAt(0)) {
                            sb.setCharAt(j, '2');
                        }
                    }
                    storageWithMargin[i] = sb.toString();
                }
            }
        }

        for (int i = 0; i < storageWithMargin.length; i++) {
            StringBuilder sb = new StringBuilder(storageWithMargin[i]);
            for (int j = 0; j < sb.length(); j++) {
                if (sb.charAt(j) != '0' && sb.charAt(j) != '2') {
                    answer++;
                }
            }
        }

        return answer;
    }
}

public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] storage = {"AZWQY", "CAABX", "BBDDA", "ACACA"};
        String[] requests = {"A", "BB", "A"};
        int result = solution.solution(storage, requests);
        System.out.println(result);
    }
}