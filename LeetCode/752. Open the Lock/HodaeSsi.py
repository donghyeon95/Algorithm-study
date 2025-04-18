class Solution:
	def openLock(self, deadends: List[str], target: str) -> int:
		deadends = set(deadends)

		if "0000" in deadends or target in deadends:
			return -1

		deque = [] # [문자열, depth]
		visited = set()

		deque.append(["0000", 0])
		visited.add("0000")

		if target == "0000":
			return 0

		while(deque):
			now, depth = deque.pop(0)

			for i in range(4):
				for j in [-1, 1]:
					next = now[:i] + str((int(now[i]) + j) % 10) + now[i+1:]

					if next == target:
						return depth + 1

					if next not in visited and next not in deadends:
						deque.append([next, depth + 1])
						visited.add(next)

		return -1