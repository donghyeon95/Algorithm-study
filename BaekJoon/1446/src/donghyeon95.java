import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());
        Set<Integer> set = new TreeSet<>();

        // 2차원 배열 대신 Map 사용 (메모리 절감)
        Map<Integer, Integer>[] distArr = new HashMap[10001];
        for (int i = 0; i <= 10000; i++) distArr[i] = new HashMap<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            if (end > D) continue;

            set.add(start);
            set.add(end);

            // 최소 거리 유지 (distArr[start].put(end, dist))
            distArr[start].merge(end, dist, Math::min);
        }

        set.add(D);

        var sortedSet = set.stream().sorted().collect(Collectors.toList());

        int[] answers = new int[D + 1];
        Arrays.fill(answers, Integer.MAX_VALUE);
        answers[0] = 0;

        int lastedNode = 0;

        for (int node : sortedSet) {
            for (int i = 0; i <= node; i++) { // N → D
                if (distArr[i].containsKey(node)) {
                    int dist = distArr[i].get(node);
                    if (answers[i] == Integer.MAX_VALUE)
                        answers[node] = Math.min(answers[node], dist);
                    else
                        answers[node] = Math.min(anslwers[node], answers[i] + dist);
                }
            }

            if (answers[lastedNode] == Integer.MAX_VALUE)
                answers[node] = Math.min(answers[node], node - lastedNode);
            else
                answers[node] = Math.min(answers[node], answers[lastedNode] + (node - lastedNode));

            lastedNode = node;
        }

        System.out.println(answers[D]);
    }
}
