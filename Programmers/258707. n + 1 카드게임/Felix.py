MOD = 10_007


def solution(n, tops):
    free = 1
    prison = 1

    for top in tops:
        if top == 0:
            free, prison = (2 * free + prison) % MOD, (free + prison) % MOD
            continue

        free, prison = (3 * free + 2 * prison) % MOD, (free + prison) % MOD

    return (free + prison) % MOD
