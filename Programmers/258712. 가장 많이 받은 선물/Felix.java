import java.util.*;

class Solution {
    public int solution(String[] friends, String[] gifts) {
        HashMap<String, Integer> friendsIdx = new HashMap<>();

        for (int i = 0; i < friends.length; i++) {
            String friend = friends[i];
            friendsIdx.put(friend, i);
        }

        int[][] dp = new int[friends.length][friends.length];
        int[] givens = new int[friends.length];

        for (int i = 0; i < gifts.length; i++) {
            String gift = gifts[i];

            String[] history = gift.split(" ");

            String giver = history[0];
            String receiver = history[1];

            Integer giverIdx = friendsIdx.get(giver);
            Integer receiverIdx = friendsIdx.get(receiver);

            dp[giverIdx][receiverIdx]++;
            givens[giverIdx]++;
            givens[receiverIdx]--;
        }

        int answer = 0;

        for (int i = 0; i < friends.length; i++) {
            int receive = 0;
            for (int j = 0; j < friends.length; j++) {
                if (dp[j][i] < dp[i][j] || (dp[i][j] == dp[j][i] && givens[j] < givens[i])) {
                    receive++;
                }
            }

            if (answer < receive) {
                answer = receive;
            }
        }

        return answer;
    }

}