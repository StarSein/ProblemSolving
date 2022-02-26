import sys


def input():
    return sys.stdin.readline().rstrip()


def main():
    def dfs(current_node: int, depth: int) -> bool:
        if depth == 4:
            return True

        is_visited[current_node] = True

        for next_node in connected[current_node]:
            if not is_visited[next_node]:
                if dfs(next_node, depth + 1):
                    return True
        return False

    n, m = map(int, input().split())
    connected = [[] for node in range(n)]
    for edge in range(m):
        a, b = map(int, input().split())
        connected[a].append(b)
        connected[b].append(a)

    is_visited = [False] * n
    for node in range(n):
        if len(connected[node]) == 1 and not is_visited[node]:
            if dfs(node, 0):
                print(1)
                break
    else:
        print(0)


if __name__ == '__main__':
    main()
