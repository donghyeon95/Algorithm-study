import java.util.*;

class Solution {
	public int solution(String[] friends, String[] gifts) {
		int friednN = friends.length;
		HashMap<String, Integer> fIdx = new HashMap<>();
		for (int i=0; i<friednN; i++) {
			String friend =  friends[i];
			fIdx.put(friend, i);
		}


		int[][] giftsList = new int[friednN][friednN];
		// 선물 주고 받은 내역
		for (String gift: gifts) {
			String[] fromTo = gift.split(" ");
			giftsList[fIdx.get(fromTo[0])][fIdx.get(fromTo[1])] += 1;
		}

		// 선물 지수 계산
		int[] giftNum = new int[friednN];
		for (int i=0; i<friednN; i++){
			int answer = 0;
			for (int j=0; j<friednN; j++) {
				if (i==j) continue;
				answer += giftsList[i][j] - giftsList[j][i];
			}
			giftNum[i] = answer;
		}


		// 선물 주고 받은 내역을 보면서 Check;
		int[] result = new int[friednN];
		for (int i=0; i<friednN; i++){
			for (int j=i+1; j<friednN; j++) {
				if (i==j) continue;


				if(giftsList[i][j]!=0 || giftsList[j][i]!=0) { // 주고 받은 기록이 있는 경우
					if (giftsList[i][j] > giftsList[j][i]) {
						result[i] += 1;
					} else if(giftsList[i][j] < giftsList[j][i]) {
						result[j] += 1;
					} else {
						// 선물 지수 확인
						if(giftNum[i]>giftNum[j]) result[i] += 1;
						else if(giftNum[i]<giftNum[j]) result[j] += 1;
					}


				} else {// 주고 받은 기록이 없는 경우
					// 선물 지수 확인
					if(giftNum[i]>giftNum[j]) result[i] += 1;
					else if(giftNum[i]<giftNum[j]) result[j] += 1;
				}

			}
		}

		return Arrays.stream(result).max().getAsInt();
	}
}