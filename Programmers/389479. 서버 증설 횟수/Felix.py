# https://school.programmers.co.kr/learn/courses/30/lessons/389479

from collections import deque
from itertools import repeat


def solution(players, m, k):
    queue = deque()
    queue.extend(repeat(0, k))

    curr_server = 0
    ret = 0

    for p in players:
        req = p // m
        curr_server -= queue.popleft()

        if curr_server < req:
            new = req - curr_server
            curr_server = req
            ret += new
            queue.append(new)
        else:
            queue.append(0)

    return ret


assert 7 == solution([0, 2, 3, 3, 1, 2, 0, 0, 0, 0, 4, 2,
                     0, 6, 0, 4, 2, 13, 3, 5, 10, 0, 1, 5], 3, 5)
assert 11 == solution([0, 0, 0, 10, 0, 12, 0, 15, 0, 1,
                      0, 1, 0, 0, 0, 5, 0, 0, 11, 0, 8, 0, 0, 0], 5, 1)
assert 12 == solution([0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0,
                      5, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1], 1, 1)
