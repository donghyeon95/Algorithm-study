import java.util.*;

class Solution {
	public int[] solution(int[][] edges) {
		HashMap<Integer, ArrayList<Integer>> inDegree = new HashMap<>();
		HashMap<Integer, ArrayList<Integer>> outDegree = new HashMap<>();
		Set<Integer> nodes = new HashSet<>();

		for (int[] edge : edges) {
			int from = edge[0];
			int to = edge[1];

			inDegree.putIfAbsent(to, new ArrayList<>());
			outDegree.putIfAbsent(from, new ArrayList<>());

			inDegree.get(to).add(from);
			outDegree.get(from).add(to);

			nodes.add(from);
			nodes.add(to);
		}

		int makedNode = 0;
		ArrayList<Integer> eights = new ArrayList<>();

		for (int node : nodes) {
			if (outDegree.getOrDefault(node, new ArrayList<>()).size() > 1
				&& !inDegree.containsKey(node)) {
				makedNode = node;
			} else if (outDegree.getOrDefault(node, new ArrayList<>()).size() > 1) {
				eights.add(node);
			}
		}

		nodes.remove(makedNode);

		for (int node : outDegree.getOrDefault(makedNode, new ArrayList<>())) {
			List<Integer> list = inDegree.get(node);
			if (list != null) {
				list.remove(Integer.valueOf(makedNode));
				if (list.isEmpty()) {
					inDegree.remove(node);
				}
			}
		}

		ArrayList<Integer> stickList = new ArrayList<>();
		for (int node : nodes) {
			if (!inDegree.containsKey(node)) {
				stickList.add(node);
			}
		}

		int stickCnt = stickList.size();
		for (int node : stickList) {
			removeStick(node, nodes, outDegree);
		}

		int eightCnt = eights.size();
		for (int node : eights) {
			nodes.remove(node);

			HashSet<Integer> visited = new HashSet<>();
			visited.add(node);

			List<Integer> children = outDegree.getOrDefault(node, new ArrayList<>());
			for (int child : children) {
				removeEight(child, nodes, outDegree, visited);
			}
		}

		int doughnutCnt = 0;
		for (int node : new HashSet<>(nodes)) {
			if (nodes.contains(node)) {
				removeEight(node, nodes, outDegree, new HashSet<>());
				doughnutCnt++;
			}
		}

		return new int[] { makedNode, doughnutCnt, stickCnt, eightCnt };
	}

	public void removeStick(int node, Set<Integer> nodes, HashMap<Integer, ArrayList<Integer>> outDegree) {
		nodes.remove(node);
		List<Integer> children = outDegree.get(node);
		if (children != null && !children.isEmpty()) {
			removeStick(children.get(0), nodes, outDegree);
		}
	}

	public void removeEight(int node, Set<Integer> nodes, HashMap<Integer, ArrayList<Integer>> outDegree,
		HashSet<Integer> visited) {
		if (visited.contains(node))
			return;
		nodes.remove(node);
		visited.add(node);
		List<Integer> children = outDegree.get(node);
		if (children != null && !children.isEmpty()) {
			removeEight(children.get(0), nodes, outDegree, visited);
		}
	}
}
