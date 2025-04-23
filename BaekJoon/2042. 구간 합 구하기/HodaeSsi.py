class SegmentTree:
    def __init__(self, data):
        self.n = len(data)
        self.size = 1
        while self.size < self.n:
            self.size *= 2
        self.tree = [0] * (2 * self.size)
        
        # 리프 노드에 데이터 할당
        for i in range(self.n):
            self.tree[self.size + i] = data[i]
            
        # 내부 노드 값 계산
        for i in range(self.size - 1, 0, -1):
            self.tree[i] = self.tree[2 * i] + self.tree[2 * i + 1]
    
    def update(self, pos, value):
        pos += self.size
        self.tree[pos] = value
        pos //= 2
        
        while pos >= 1:
            self.tree[pos] = self.tree[2 * pos] + self.tree[2 * pos + 1]
            pos //= 2
    
    def query(self, l, r):
        res = 0
        l += self.size
        r += self.size
        
        while l <= r:
            if l % 2 == 1:
                res += self.tree[l]
                l += 1
            if r % 2 == 0:
                res += self.tree[r]
                r -= 1
            l //= 2
            r //= 2
            
        return res

N, M, K = map(int, input().split())

arr = [0] * (N)
for i in range(N):
	arr[i] = int(input())
     
# 세그먼트 트리 구성
st = SegmentTree(arr)

for i in range(M + K):
	query = list(map(int, input().split()))
	if query[0] == 1: # 업데이트
		st.update(query[1] - 1, query[2])
	elif query[0] == 2: # 구간 합
		print(st.query(query[1] - 1, query[2] - 1))
