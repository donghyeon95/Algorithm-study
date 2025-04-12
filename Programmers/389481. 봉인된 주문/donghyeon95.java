// import java.util.Arrays;
// class Solution {
// 	public String solution(long n, String[] bans) {
// 		long strLen = 1;
// 		long accCnt = 0;
// 		while (true) {
// 			int banCnt = countBans(bans, strLen);
// 			long lenCount = calcCountFromLen(strLen) - banCnt;
// 			if (accCnt < n && accCnt + lenCount >= n) break;
// 			else accCnt += lenCount;
// 			strLen++;
// 		}
//
// 		return checkString(accCnt, strLen, n, bans);
// 	}
//
// 	public String checkString(long accCount, long strlen, long n, String[] bans) {
// 		long diff = n - accCount;
// 		char[] str = new char[(int)strlen];
// 		Arrays.fill(str, 'a');
//
// 		while (true) {
// 			// ban 리스트에 포함되지 않으면 diff를 줄임
// 			if (!Arrays.asList(bans).contains(new String(str))) {
// 				diff--;
// 				if (diff == 0) break;
// 			}
// 			increment(str);
// 		}
// 		return new String(str);
// 	}
//
// 	public void increment(char[] str) {
// 		int idx = str.length - 1;
// 		while (idx >= 0) {
// 			str[idx]++;
// 			if (str[idx] <= 'z') break;
// 			str[idx] = 'a';
// 			idx--;
// 		}
// 	}
//
// 	public long calcCountFromLen(long len) {
// 		return (long)Math.pow(26, len);
// 	}
//
// 	public int countBans(String[] bans, long len) {
// 		int count = 0;
// 		for (String ban : bans) {
// 			if (ban.length() == len) count++;
// 		}
// 		return count;
// 	}
// }


// import java.util.*;
//
// class Solution {
// 	private Map<Integer, Integer> banLengthCount;
// 	private Set<String> banSet;
// 	private Map<Long, Long> powerMemo = new HashMap<>();
//
// 	public String solution(long n, String[] bans) {
// 		prepareBanData(bans);
//
// 		long strLen = 1;
// 		long accCnt = 0;
//
// 		while (true) {
// 			int banCnt = countBans(strLen);
// 			long lenCount = calcCountFromLen(strLen) - banCnt;
// 			if (accCnt + lenCount >= n) break;
// 			accCnt += lenCount;
// 			strLen++;
// 		}
//
// 		return checkString(accCnt, strLen, n);
// 	}
//
// 	private void prepareBanData(String[] bans) {
// 		banLengthCount = new HashMap<>();
// 		banSet = new HashSet<>();
// 		for (String ban : bans) {
// 			banSet.add(ban);
// 			int len = ban.length();
// 			banLengthCount.put(len, banLengthCount.getOrDefault(len, 0) + 1);
// 		}
// 	}
//
// 	private long calcCountFromLen(long len) {
// 		if (powerMemo.containsKey(len)) return powerMemo.get(len);
// 		long result = 1;
// 		for (int i = 0; i < len; i++) result *= 26;
// 		powerMemo.put(len, result);
// 		return result;
// 	}
//
// 	private int countBans(long len) {
// 		return banLengthCount.getOrDefault((int) len, 0);
// 	}
//
// 	private String checkString(long accCount, long strlen, long n) {
// 		long diff = n - accCount;
// 		char[] str = new char[(int) strlen];
// 		Arrays.fill(str, 'a');
//
// 		while (true) {
// 			// banSet 탐색은 문자열이 완성된 순간에만!
// 			if (!banSet.contains(new String(str))) {
// 				diff--;
// 				if (diff == 0) return new String(str);
// 			}
// 			increment(str);
// 		}
// 	}
//
// 	private void increment(char[] str) {
// 		int idx = str.length - 1;
// 		while (idx >= 0) {
// 			str[idx]++;
// 			if (str[idx] <= 'z') break;
// 			str[idx] = 'a';
// 			idx--;
// 		}
// 	}
// }



import java.util.*;

class Solution {
	public String solution(long n, String[] bans) {
		List<String> banList = new ArrayList<>(Arrays.asList(bans));

		banList.sort(Comparator
			.comparingInt(String::length)
			.thenComparing(Comparator.naturalOrder()));


		while (!banList.isEmpty()) {
			String candidate = getNthString(n);
			String ban = banList.get(0);
			int cmp = compareLex(ban, candidate);

			if (cmp <= 0) {
				banList.remove(0);
				n++; // ban이 앞에 있었으므로 보정
			} else {
				break;
			}
		}

		return getNthString(n);

	}

	private String getNthString(long n) {
		long len = 1;
		long acc = 0;

		while (true) {
			long count = pow26(len);
			if (acc + count >= n) break;
			acc += count;
			len++;
		}

		long offset = n - acc - 1;
		return toBase26(offset, (int) len);
	}

	private String toBase26(long num, int length) {
		char[] result = new char[length];
		for (int i = length - 1; i >= 0; i--) {
			result[i] = (char) ('a' + (num % 26));
			num /= 26;
		}
		return new String(result);
	}

	private long pow26(long len) {
		long result = 1;
		for (int i = 0; i < len; i++) result *= 26;
		return result;
	}

	private int compareLex(String a, String b) {
		if (a.length() != b.length()) return a.length() - b.length();
		return a.compareTo(b);
	}
}
