class Solution {
	public int solution(int[] schedules, int[][] timelogs, int startday) {
		int answer = 0;

		for (int i=0; i<schedules.length; i++) {
			int limitTime = getLimit(schedules[i]);

			int[] timelog = timelogs[i];
			boolean result = true;
			for (int j=0; j<timelog.length; j++) {
				int day = (startday+j)%7;
				if (day==6 || day==0) continue;
				if (limitTime < timelog[j]) {
					result = false;
				}
			}
			if (result) answer++;
		}

		return answer;
	}

	public int getLimit(int goalTime) {
		return (goalTime+10) % 100 >= 60? goalTime+100-50: goalTime+10;
	}

}