def solution(edges):
    answer = [0] * 4

    adj_map = {} # key - 노드 번호, value - [들어오는 간선 수, 나가는 간선 수, [다음 노드 리스트]]
    for edge in edges:
        node1 = adj_map.get(edge[0], [0, 0, []])
        node1[1] += 1
        node1[2].append(edge[1])
        adj_map[edge[0]] = node1

        node2 = adj_map.get(edge[1], [0, 0, []])
        node2[0] += 1
        adj_map[edge[1]] = node2

    for node in adj_map.keys():
        if adj_map[node][0] == 0 and adj_map[node][1] >= 2:
            answer[0] = node

    for node in adj_map[answer[0]][2]:
        answer[dfs(adj_map, {}, node)] += 1

    return answer

def dfs(adj_map, visited_map, now_node):
    stack = [now_node]

    while stack:
        now_node = stack.pop()

        next_node_list = adj_map[now_node][2]

        if adj_map[now_node][1] >= 2:
            return 3

        for next_node in next_node_list:
            if adj_map[next_node][1] >= 2:
                return 3

        if len(next_node_list) == 0:
            return 2

        for next_node in next_node_list:
            if visited_map.get(next_node, False):
                return 1

        visited_map[now_node] = True

        stack.append(next_node_list[0])

# test main
if __name__ == "__main__":
    print(solution(	[[4, 11], [1, 12], [8, 3], [12, 7], [4, 2], [7, 11], [4, 8], [9, 6], [10, 11], [6, 10], [3, 5], [11, 1], [5, 3], [11, 9], [3, 8]]))