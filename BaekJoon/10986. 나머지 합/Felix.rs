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

fn solve(n: usize, m: usize, nums: Vec<usize>) -> usize {
    let mut cache = vec![0; m];
    cache[0] = 1;

    nums.into_iter().fold(0, |acc, curr| {
        let next = (acc + curr) % m;
        cache[next] += 1;
        return next;
    });

    cache
        .into_iter()
        .filter(|num| 0 < *num)
        .fold(0, |acc, cnt| acc + cnt * (cnt - 1) / 2)
}
fn main() {
    let mut input = input::<usize, Vec<_>, VecDeque<_>>();

    let line = input.pop_front().unwrap();

    let (n, m) = (line[0], line[1]);

    let nums = input.pop_front().unwrap();

    let answer = solve(n, m, nums);

    println!("{}", answer);
}
