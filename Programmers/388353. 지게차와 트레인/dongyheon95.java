import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

// BLANK 주위 박스들 LIST <>
// LIST<NODE>
// 없어지면 Blank 업데이트 => LIST, 배열에도
// 크레인으로 뽑은 것 중 BLACK가 아닌 경우 EMPTY로
// BLANK 업데이트 시 EMPTY는 BLACK로 변경 하고 다시 주변 BLANK로


class Solution {
	private final String BLANK = ".";
	private final String EMPTY = "~";
	private final int[][] MOVES = {{0,1}, {1,0}, {0,-1}, {-1,0}};

	public int solution(String[] storage, String[] requests) {
		int answer = 0;

		// Init Arr
		String[][] storages = new String[storage.length][storage[0].length()];
		for (int i=0; i<storage.length; i++) {
			storages[i] = storage[i].split("");
		}


		List<Container> accessables = initAccessable(storages);

		for (String req: requests) {
			if (req.length() == 1) {
				// Blank 주변에서
				List<Container> filteredContainer = accessables.stream()
					.filter(container -> Objects.equals(container.getStr(), req))
					.collect(Collectors.toList());
				filteredContainer.forEach(c -> updateStorage(c, storages, accessables));
			} else {
				String str = Character.toString(req.charAt(0));
				// List 먼저 뺴고
				List<Container> filteredContainer = accessables.stream().filter(container -> Objects.equals(container.getStr(), req)).collect(
					Collectors.toList());
				filteredContainer.forEach(c -> updateStorage(c, storages, accessables));

				// storage에서 빼고
				craneStorage(storages, str, accessables);
			}
		}

		//남은 Container 계산
		for (int i = 0; i<storages.length; i++) {
			for (int j=0; j<storages[0].length; j++) {
				if (!storages[i][j].equals(BLANK) && !storages[i][j].equals(EMPTY))
					answer++;
			}
		}

		return answer;
	}

	public void craneStorage(String[][] storages, String s, List<Container> accessables) {
		// storages를 탐색하면서 S 인 것을 Empty로 변경
		for (int i = 0; i<storages.length; i++) {
			for (int j=0; j<storages[0].length; j++) {
				if (storages[i][j].equals(s)){
					// 해당 container를 EMPTY로 할 지 BLANK로 할 지 판단.
					boolean isBLANK = false;

					for (int[] move: MOVES) {
						int y = i + move[0];
						int x = j + move[1];

						if (y<0 || y>=storages.length || x<0 || x>=storages[0].length || storages[y][x].equals(BLANK)) {
							isBLANK = true;
						}
					}

					if (isBLANK) {
						updateStorage(new Container(j,i, storages[i][j]), storages, accessables);
					}else {
						storages[i][j] = EMPTY;
					}
				}

			}
		}

	}

	public void updateStorage(Container c, String[][] storeages, List<Container> accessables) {
		// 해당 컨테이너의 위치를 "BLANK"로 변경
		int x = c.getX();
		int y = c.getY();
		storeages[y][x] = BLANK;

		// accessable에서 해당 컨테이너 제거
		accessables.removeIf(container -> container.equals(c));

		Queue<Container> queue = new ArrayDeque<>();

		// 주위의 Container를 accesables로 add
		for (int[] move: MOVES) {
			int newY = y + move[0];
			int newX = x + move[1];

			if (newX<0 || newX>=storeages[0].length || newY<0 || newY>=storeages.length) continue;
			if (storeages[newY][newX].equals(BLANK)) continue;

			queue.add(new Container(newX, newY, storeages[newY][newX]));
		}

		// 이때, Empty를 만나면 한 뎁스 더 => Queue를 통해서 bfs로 업데이트 하자.
		while (!queue.isEmpty()) {
			Container con = queue.poll();

			if (con.getStr().equals(EMPTY)) {
				int cx  = con.getX();
				int cy = con.getY();

				storeages[cy][cx] = BLANK;

				for (int[] move: MOVES) {
					int newY = cy + move[0];
					int newX = cx + move[1];

					if (newX<0 || newX>=storeages[0].length || newY<0 || newY>=storeages.length) continue;
					if (storeages[newY][newX].equals(BLANK)) continue;

					queue.add(new Container(newX, newY, storeages[newY][newX]));
				}
			} else {
				accessables.add(con);
			}
		}
	}

	public List<Container> initAccessable(String[][] storages) {
		List<Container> accessables = new ArrayList<>();


		// init
		for (int i = 0; i < storages[0].length; i++) {
			accessables.add(new Container(i, 0, storages[0][i]));
		}

		if (storages.length>2) {
			for (int i=1; i<storages.length-1; i++) {
				accessables.add(new Container(0, i, storages[i][0]));
				accessables.add(new Container(storages[i].length-1, i, storages[i][storages[i].length-1]));
			}
		}

		// 하단
		for (int i = 0; i < storages[storages.length-1].length; i++) {
			accessables.add(new Container(i, storages.length-1, storages[storages.length-1][i]));
		}

		return accessables;
	}

	class Container {

		private int x;
		private int y;
		private String str;

		public Container(int x, int y, String str) {
			this.x = x;
			this.y = y;
			this.str = str;
		}

		public String getStr() {
			return str;
		}

		public int getY() {
			return y;
		}

		public int getX() {
			return x;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			Container c = (Container) obj;
			return x == c.x && y == c.y && Objects.equals(str, c.str);
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y, str);
		}

	}
}