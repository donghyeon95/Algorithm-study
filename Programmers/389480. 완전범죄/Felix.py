# https://school.programmers.co.kr/learn/courses/30/lessons/389480

from sys import maxsize
from typing import List, Tuple


class inf(int):
    def __new__(self):
        return super(inf, self).__new__(self, maxsize)

    def __repr__(self):
        return 'inf'


INF = inf()


def solution(info: List[Tuple[int, int]], n: int, m: int):
    dp = [[INF] * 125 for _ in range(125)]
    dp[0][0] = 0

    def new_dp(): return [[INF] * 125 for _ in range(125)]

    for na, nb in info:
        next_dp = new_dp()

        for a in range(120):
            for b in range(120):
                next_dp[a+na][b] = min(next_dp[a+na][b], dp[a][b] + 1)
                next_dp[a][b+nb] = min(next_dp[a][b+nb], dp[a][b])

        dp = next_dp

    for x in range(n):
        for y in range(m):
            if dp[x][y] != INF:
                return x

    return -1


assert 2 == solution([[1, 2], [2, 3], [2, 1]], 4, 4)
assert 0 == solution([[1, 2], [2, 3], [2, 1]], 1, 7)
assert 6 == solution([[3, 3], [3, 3]], 7, 1)
assert -1 == solution([[3, 3], [3, 3]], 6, 1)
