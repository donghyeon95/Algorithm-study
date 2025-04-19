use std::collections::VecDeque;

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

enum Query {
    Update(usize, isize),
    Sum(usize, usize),
}

fn solve(n: usize, nums: Vec<isize>, queries: Vec<Query>) -> Vec<isize> {
    let mut segment_tree = vec![0; n];
    segment_tree.extend(nums.into_iter());

    for node in (1..n).rev() {
        let left = 2 * node;
        let right = 2 * node + 1;
        segment_tree[node] = segment_tree[left] + segment_tree[right];
    }

    let mut ret = vec![];

    for query in queries {
        match query {
            Query::Update(idx, num) => {
                let mut node = idx + n - 1;
                segment_tree[node] = num;

                while 1 < node {
                    node /= 2;
                    let left = 2 * node;
                    let right = 2 * node + 1;

                    segment_tree[node] = segment_tree[left] + segment_tree[right];
                }
            }
            Query::Sum(mut left, mut right) => {
                left += n - 1;
                right += n - 1;

                let mut sum = 0;

                while left <= right {
                    if left % 2 == 1 {
                        sum += segment_tree[left];
                        left += 1;
                    }

                    if right % 2 == 0 {
                        sum += segment_tree[right];
                        right -= 1;
                    }

                    left /= 2;
                    right /= 2;
                }

                ret.push(sum);
            }
        }
    }

    ret
}

fn main() {
    let mut input = input::<isize, Vec<_>, VecDeque<_>>();
    let line = input.pop_front().unwrap();

    let (n, m, k) = (line[0] as usize, line[1] as usize, line[2] as usize);

    let nums = input.drain(0..n).map(|line| line[0]).collect::<Vec<_>>();

    let queries = input
        .drain(0..(m + k))
        .map(|line| match line[0] {
            1 => Query::Update(line[1] as usize, line[2]),
            2 => Query::Sum(line[1] as usize, line[2] as usize),
            _ => unreachable!(),
        })
        .collect::<Vec<_>>();

    let answer = solve(n, nums, queries);

    println!(
        "{}",
        answer
            .iter()
            .map(|x| x.to_string())
            .collect::<Vec<_>>()
            .join("\n")
    );
}
