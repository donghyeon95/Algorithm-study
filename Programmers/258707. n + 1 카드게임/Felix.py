def solution(coin, cards):
    n = len(cards)

    goal = n+1

    hand = set(cards[:n//3])
    left = list(reversed(cards[n//3:]))
    candidates = set()

    for turn in range(1, 1 + n//3):
        candidates.add(left.pop())
        candidates.add(left.pop())

        found = False
        for card in hand:
            if goal - card in hand:
                found = True
                break
        if found:
            hand.remove(card)
            hand.remove(goal - card)
            continue

        if coin == 0:

            return turn

        for card in hand:
            if goal - card in candidates:
                found = True
                break

        if found:
            hand.remove(card)
            candidates.remove(goal - card)
            coin -= 1
            continue

        if coin == 1:

            return turn

        for card in candidates:
            if goal - card in candidates:
                found = True
                break

        if found:
            candidates.remove(card)
            candidates.remove(goal - card)
            coin -= 2
            continue

        return turn

    return n//3 + 1
