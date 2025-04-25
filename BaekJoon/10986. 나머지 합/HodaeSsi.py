# 시간 복잡도 : O(n)
# 추가 공간 복잡도 : O(n)

n, m = map(int, input().split())
# n, m = 5, 3
numbers = list(map(int, input().split()))
# numbers = [1, 2, 3, 1, 2]

# 누적 합 구하기(각 원소에 저장할 값은 modulo m으로 나눈 나머지)
prefix_sum = [0] * (n + 1)
for i in range(1, n + 1):
	prefix_sum[i] = (prefix_sum[i - 1] + numbers[i - 1] % m) % m

remainder_count = [0] * m # idx - 나머지, value - 그 나머지의 개수
for i in range(n + 1):
	remainder_count[prefix_sum[i]] += 1

result = 0
for i in range(m):
	result += remainder_count[i] * (remainder_count[i] - 1) // 2 # nC2 조합의 수
print(result)
