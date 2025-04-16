from typing import List
from itertools import combinations, product
from collections import defaultdict


def solution(arr_dice: List[List[int]]) -> List[int]:
    n = len(arr_dice)
    half = n // 2

    picks = list(combinations(range(n), half))
    choices = tuple(product(range(6), repeat=half))
    pick_points = defaultdict(lambda: defaultdict(int))

    for pick in picks:
        picked_dice = [arr_dice[i] for i in pick]

        for choice in choices:
            points = 0
            for dice_idx, c in enumerate(choice):
                points += picked_dice[dice_idx][c]

            pick_points[pick][points] += 1

    curr_max = 0
    for pick in picks:
        other_pick = tuple(i for i in range(n) if i not in pick)

        one = pick_points[pick]
        other = pick_points[other_pick]

        wins = 0

        for one_point, one_occasion in one.items():
            for other_point, other_occasion in other.items():
                if other_point < one_point:
                    wins += one_occasion * other_occasion

        if curr_max < wins:
            curr_max = wins
            ret = sorted(pick)

    return [i+1 for i in ret]


assert [1, 4] == solution([[1, 2, 3, 4, 5, 6], [3, 3, 3, 3, 4, 4],
                           [1, 3, 3, 4, 4, 4], [1, 1, 4, 4, 5, 5]])

assert [2] == solution([[1, 2, 3, 4, 5, 6], [2, 2, 4, 4, 6, 6]])

assert [1, 3] == solution([[40, 41, 42, 43, 44, 45], [43, 43, 42, 42, 41, 41],
                           [1, 1, 80, 80, 80, 80], [70, 70, 1, 1, 70, 70]])
