N, M = map(int, input().split())

graph = {} # key - 노드 번호, value - [진입차수, [자식 노드들]]
for i in range(1, N + 1):
	graph[i] = [0, []]

for _ in range(M):
	a, b = map(int, input().split())
	graph[a][1].append(b)
	graph[b][0] += 1

# bfs
q = []
for i in range(1, N + 1):
	if graph[i][0] == 0:
		q.append(i)
		print(i, end=' ')

while q:
	node = q.pop(0)
	for child in graph[node][1]:
		graph[child][0] -= 1
		if graph[child][0] == 0:
			q.append(child)
			print(child, end=' ')

