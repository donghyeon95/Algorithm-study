class Solution {
	public int solution(int n, int w, int num) {
		int answer = 0;
		int cur = num;
		int x = 0;
		int y = (num-1)/w+1;
		boolean flag = true;

		if (y%2 == 0) {
			flag = true; // 오른으로 이동
			x = w-(w*y-num);
		} else {
			flag =false;
			x = w*y-num+1;
		} // 왼쪽으로 이동

		while(cur <= n) {
			answer++;
			if (flag) {
				cur+= 2*(w-x)+1;
				flag = false;
			}  else {
				cur+=2*(x-1)+1;
				flag=true;
			}
		}

		return answer;
	}
}