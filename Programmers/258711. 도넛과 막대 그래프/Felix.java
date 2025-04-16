// https://school.programmers.co.kr/learn/courses/30/lessons/258711
// 도넛과 막대 그래프

import java.util.*;

class Solution {
    public int[] solution(int[][] edges) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        HashMap<Integer, List<Integer>> reversedGraph = new HashMap<>();
        HashSet<Integer> nodes = new HashSet<>();
        HashSet<Integer> visited = new HashSet<>();

        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];

            nodes.add(a);
            nodes.add(b);

            if (!reversedGraph.containsKey(b))
                reversedGraph.put(b, new ArrayList<>());

            reversedGraph.get(b).add(a);

            if (!graph.containsKey(a))
                graph.put(a, new ArrayList<>());

            graph.get(a).add(b);
        }

        int root = 0;
        int straight = 0;
        int donut = 0;
        int eight = 0;
        List<Integer> entries = new ArrayList<>();

        for (Integer node : nodes) {
            if (!reversedGraph.containsKey(node)) {
                if (graph.containsKey(node) && 2 <= graph.get(node).size()) {
                    root = node;
                    entries = graph.get(node);
                    break;
                }
            }
        }

        for (Integer node : entries) {
            if (!graph.containsKey(node)) {
                straight++;
                continue;
            }

            boolean isEight = false;
            boolean isStraight = true;

            visited.add(node);
            Queue<Integer> queue = new LinkedList<>();

            queue.add(node);

            while (!queue.isEmpty()) {
                int curNode = queue.poll();

                List<Integer> nextNodes = graph.getOrDefault(curNode, new ArrayList<>());

                if (2 <= nextNodes.size()) {
                    isEight = true;
                }

                for (int nextNode : nextNodes) {
                    if (!visited.contains(nextNode)) {
                        visited.add(nextNode);
                        queue.add(nextNode);
                    } else {
                        isStraight = false;
                    }

                }

            }

            if (isStraight) {
                straight++;
            } else if (isEight) {
                eight++;
            } else {
                donut++;
            }

        }

        int[] answer = { root, donut, straight, eight };
        return answer;
    }
}
