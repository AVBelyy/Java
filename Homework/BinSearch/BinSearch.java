public class BinSearch {
    static int[] a;

    static int recurSearch(int x, int l, int r) {
        // pre:  a is sorted (for all i in [0..n-2]: a[i] <= a[i + 1]
        //       a is not empty (a.length > 0)
        //       l <= r
        //       l >= 0
        //       r <= a.length - 1
        //       a[l] < x <= a[r]
        // post: r = min(i: a[i] >= x)
        if (x <= a[0]) {
            return 0;
        } else if (x > a[a.length - 1]) {
            return a.length;
        } else {
            int m = l + (r - l) / 2;
            if (r - l <= 1) {
                return r;
            } else if (x <= a[m]) {
                return recurSearch(x, l, m);
            } else {
                return recurSearch(x, m, r);
            }
        }
    }

    static int iterSearch(int x) {
        // pre:  a is sorted (for all i in [0..n-2]: a[i] <= a[i + 1]
        //       a is not empty (a.length > 0)
        // post: r = max(i: a[i] >= x)
        if (x < a[0]) {
            return 0;
        } else if (x >= a[a.length - 1]) {
            return a.length - 1;
        } else {
            int l = 0, r = a.length - 1, m;
            // inv: a[l] <= x < a[r]
            while (r - l > 1) {
                m = l + (r - l) / 2;
                if (x < a[m]) {
                    r = m;
                } else {
                    l = m;
                }
            }
            return l;
        }
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        a = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        if (a.length == 0) {
            System.out.println("0 0\n");
        } else {
            int l = recurSearch(x, 0, a.length - 1);
            int r = iterSearch(x);
            if (l < a.length && a[l] == x) {
                //System.out.printf("count: %d\noccurrence: [%d..%d]\n", r - l + 1, l, r);
                System.out.printf("%d %d\n", l, r - l + 1);
            } else {
                //System.out.printf("insertion point: %d\n", l);
                System.out.printf("%d 0\n", l);
            }
        }
    }
}
