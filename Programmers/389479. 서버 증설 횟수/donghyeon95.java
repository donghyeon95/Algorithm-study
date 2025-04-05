import java.util.*;

class Solution {
	private int count = 0;

	public int solution(int[] players, int m, int k) {
		PriorityQueue<Integer> servers = new PriorityQueue<>();

		for (int i=0; i<24; i++) {
			int playerCnt = players[i];

			// 서버 제거
			removeServer(servers, i);

			// 추가로 필요한 서버 확인
			int needServerCnt = calcServerCount(playerCnt, servers.size(), m);

			// 서버 수대로 서버 증설
			for (int j=0; j<needServerCnt; j++) {
				addServer(servers, i+k);
			}
		}

		return count;
	}

	public int calcServerCount(int playerCnt, int serverCnt, int m) {
		int needServer = playerCnt/m;

		return needServer-serverCnt;
	}

	public void addServer(PriorityQueue<Integer> servers, int removingRound) {
		count++;
		servers.add(removingRound);
	}

	public void removeServer(PriorityQueue<Integer> servers, int curRound) {
		while(!servers.isEmpty() && servers.peek() <= curRound) {
			servers.poll();
		}
	}

}