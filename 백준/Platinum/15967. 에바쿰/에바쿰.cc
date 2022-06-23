#include <iostream>
using namespace std;
typedef long long ll;

const int MAX_N = 1e6+1;
ll arr[MAX_N], seg[4*MAX_N], lazy[4*MAX_N];

int n, q1, q2;

void makeSegTree(int node, int s, int e) {
    if (s == e) {
        seg[node] = arr[s];
        return;
    }
    int mid = (s + e) >> 1;
    makeSegTree(node << 1, s, mid);
    makeSegTree(node << 1 | 1, mid+1, e);
    seg[node] = seg[node << 1] + seg[node << 1 | 1];
}

void updateLazy(int node, int s, int e) {
    if (lazy[node]) {
        seg[node] += (e -s + 1) * lazy[node];
        if (s != e) {
            lazy[node << 1] += lazy[node];
            lazy[node << 1 | 1] += lazy[node];
        }
        lazy[node] = 0;
    }
}

void updateRange(int node, int s, int e, int l, int r, int v) {
    updateLazy(node, s, e);

    if (s > r || e < l) {
        return;
    }
    if (l <= s && e <= r) {
        lazy[node] = v;
        updateLazy(node, s, e);
        return;
    }
    int mid = (s + e) >> 1;
    updateRange(node << 1, s, mid, l, r, v);
    updateRange(node << 1 | 1, mid+1, e, l, r, v);
    seg[node] = seg[node << 1] + seg[node << 1 | 1];
}

ll query(int node, int s, int e, int l, int r) {
    updateLazy(node, s, e);

    if (s > r || e < l) {
        return 0;
    }
    if (l <= s && e <= r) {
        return seg[node];
    }
    int mid = (s + e) >> 1;
    return query(node << 1, s, mid, l, r) + query(node << 1 | 1, mid+1, e, l, r);
}


int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> n >> q1 >> q2;
    for (int i = 1; i <= n; i++) {
        cin >> arr[i];
    }
    makeSegTree(1, 1, n);
    int type, s, e, l;
    for (int q = 0; q < q1 + q2; q++) {
        cin >> type;
        if (type == 1) {
            cin >> s >> e;
            cout << query(1, 1, n, s, e) << '\n';
        } else {
            cin >> s >> e >> l;
            updateRange(1, 1, n, s, e, l);
        }
    }
}