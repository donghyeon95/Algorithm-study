# https://school.programmers.co.kr/learn/courses/30/lessons/389478
# 택배 상자 꺼내기


def solution(n: int, w: int, num: int) -> int:
    d = 1
    idx = 1
    col = 1000000
    found = False

    ret = 1

    for cargo in range(1, n+1):
        if found:
            if idx == col:
                ret += 1

        if num == cargo:
            col = idx
            found = True

        idx += d
        if w < idx:
            idx = w
            d *= -1
        elif idx == 0:
            idx = 1
            d *= -1

    return ret


assert solution(13, 3, 6) == 4
assert solution(22, 6, 8) == 3
