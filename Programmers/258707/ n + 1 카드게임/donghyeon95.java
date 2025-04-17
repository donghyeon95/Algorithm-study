import java.util.HashSet;

class Solution {
	public int solution(int coin, int[] cards) {
		int n = cards.length;
		HashSet<Integer> hands = new HashSet<>();
		HashSet<Integer> dead = new HashSet<>();
		System.out.println(n+1);
		// hand 초기화
		int initialHandSize = n / 3;
		for (int i=0; i<initialHandSize; i++) {
			hands.add(cards[i]);
		}

		int round = 1;
		int index = initialHandSize;
		while(index < n) {
			// 현재 손에 pair가 있는 지 확인
			int[] pair = findPair(hands, n+1);
			// 뽑을 카드는 dead로 이동
			dead.add(cards[index++]);
			dead.add(cards[index++]);
			System.out.println(hands);
			System.out.println(dead);
			System.out.println(coin);

			// 있으면
			if(pair!=null) {
				// 해당 카드 제거 하고
				hands.remove(pair[0]);
				hands.remove(pair[1]);

				round++;
				continue;
			} else {// 없으면
				int removedCard = -1;

				if (coin > 0) {
					for (int card: hands) { // coin이 1개 이상일 때, 1개만 써서 부활 가능 여부 확인
						if (dead.contains(n+1-card)) {
							coin--;
							removedCard = card;
							break;
						}
					}

					if (removedCard != -1) {
						hands.remove(removedCard);
						dead.remove(n + 1 - removedCard);
						round++;
						continue;
					} else if (coin > 1) { // 이것도 안되면 // coin이 2개 이상일 때, 2개를 써서 부활 가능 여부 확인
						int[] deadPair = findPair(dead, n+1);
						if (deadPair != null) {
							dead.remove(deadPair[0]);
							dead.remove(deadPair[1]);
							coin -= 2;
							round++;
							continue;
						}
					}
				}

			}
			break;
		}

		return round;
	}

	public int[] findPair(HashSet<Integer> set, int n) {
		for (int card: set) {
			if (set.contains(n-card))
				return new int[] {card, n - card};
		}

		return null;
	}
}