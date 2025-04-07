# https://school.programmers.co.kr/learn/courses/30/lessons/389481
# 봉인된 주문


from collections import defaultdict
from typing import List, Tuple


BASE = 26
CHARS = list('abcdefghijklmnopqrstuvwxyz')


def solution(n: int, bans: List[str]) -> str:

    len_bans = defaultdict(list)

    for ban in bans:
        len_bans[len(ban)].append(ban)

    bans = len_bans

    turn = 0

    for length in range(1, 1_000_000):
        rng = 26 ** length - len(len_bans[length])

        if n <= turn + rng:
            break

        turn += rng

    def define_char(length: int, pos: int, left: int, bans: List[str]) -> Tuple[str, int, List[str]]:
        ban_idx = 0

        if pos == 1:
            pass

        for i in range(26):
            char = CHARS[i]
            new = 26 ** (pos-1)

            while ban_idx < len(bans):
                if char < bans[ban_idx][length-pos]:
                    break
                new -= 1
                ban_idx += 1

            if left <= new:
                break
            left -= new

        return char, left, list(filter(lambda ban: ban[length - pos] == char, bans))

    ret = []
    left = n - turn
    bans = sorted(len_bans[length])

    for pos in range(length, 0, -1):
        char, left, bans = define_char(length, pos, left, bans)
        ret.append(char)

    return ''.join(ret)


assert 'ah' == solution(
    30,
    ["d", "e", "bb", "aa", "ae"]
)
assert 'jxk' == solution(
    7388,
    ["gqk", "kdn", "jxj", "jxi", "fug", "jxg", "ewq", "len", "bhc"]
)
assert solution(1, ["a"]) == "b"
assert solution(1, ["b"]) == "a"
assert solution(2, ["b"]) == "c"
assert solution(1, ["a", "c"]) == "b"
assert solution(2, ["a", "c"]) == "d"
assert solution(2, ["a", "b", "c", "f", "g"]) == "e"
assert solution(3, ["a", "b", "c", "f", "g"]) == "h"
assert solution(217180147157, ["abcd", "zefdgd"]) == "aaaaaaaaa"
assert solution(3817158266467285, ["dfdfdfd"]) == "zzzzzzzzzzz"
