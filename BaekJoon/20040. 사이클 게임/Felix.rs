fn parse<T1: std::str::FromStr, T2: std::iter::FromIterator<T1>>(line: &str) -> T2
where
    <T1 as std::str::FromStr>::Err: std::fmt::Debug,
{
    line.trim()
        .split_ascii_whitespace()
        .map(|word| T1::from_str(word).unwrap())
        .collect()
}

fn input<T1: std::str::FromStr, T2: std::iter::FromIterator<T1>, T3: std::iter::FromIterator<T2>>(
) -> T3
where
    <T1 as std::str::FromStr>::Err: std::fmt::Debug,
{
    std::io::read_to_string(std::io::stdin())
        .expect("PARSE_ERROR")
        .trim()
        .split('\n')
        .map(parse::<T1, T2>)
        .collect::<T3>()
}

fn solve(n: usize, edges: Vec<(usize, usize)>) -> usize {
    fn get_parent(parents: &mut Vec<usize>, node: usize) -> usize {
        if node == parents[node] {
            return node;
        }

        parents[node] = get_parent(parents, parents[node]);
        parents[node]
    }

    fn union_parent(parents: &mut Vec<usize>, node1: usize, node2: usize) {
        let parent1 = get_parent(parents, node1);
        let parent2 = get_parent(parents, node2);

        if parent1 <= parent2 {
            parents[parent2] = parent1
        } else {
            parents[parent1] = parent2
        }
    }

    let mut parents = (0..n).collect::<Vec<_>>();

    for (turn, (node1, node2)) in (1..).zip(edges) {
        if get_parent(&mut parents, node1) == get_parent(&mut parents, node2) {
            return turn;
        }

        union_parent(&mut parents, node1, node2);
    }

    0
}

fn main() {
    let mut input = input::<usize, Vec<_>, std::collections::VecDeque<_>>();
    let line = input.pop_front().unwrap();

    let (n, m) = (line[0], line[1]);

    let edges = input
        .drain(0..m)
        .map(|line| (line[0], line[1]))
        .collect::<Vec<_>>();

    let answer = solve(n, edges);

    println!("{}", answer);
}
