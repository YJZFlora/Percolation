import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *  only import：
 *  StdIn, StdOut, StdRandom, StdStats, WeightedQuickUnionUF
 *  java.lang.
 */

public class Percolation {
    private int sideLength;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private int v1;
    private int v2;
    private int[] openOrNot;
    private int count;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)  throw new IllegalArgumentException();
        sideLength = n;
        v1 = 0;
        v2 = n * n + 1;
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 1);
        openOrNot = new int[n * n + 1];
        // connect the first row with virtual num 0; connect the last row with num (n + 1)
        for (int i = 1; i < n + 1; i++) {
            uf.union(v1, i);
            int buttom = n * (n - 1) + i;
            uf.union(v2, buttom);
            uf2.union(v1, i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (outOfBound(row, col)) throw new IllegalArgumentException();
        if (isOpen(row, col)) return;

        int cur = positionToNum(row, col);
        openOrNot[cur] = 1;
        count ++;

        if (hasLeft(row, col)) {
            int left = positionToNum(row, col - 1);
            if (isOpen(row, col - 1)) {
                uf.union(cur, left);
                uf2.union(cur, left);
            }
        }
        if (hasRight(row, col)) {
            int right = positionToNum(row, col + 1);
            if (isOpen(row, col + 1)) {
                uf.union(cur, right);
                uf2.union(cur, right);
            }
        }
        if (hasUp(row, col)) {
            int up = positionToNum(row - 1, col);
            if (isOpen(row - 1, col)) {
                uf.union(cur, up);
                uf2.union(cur, up);
            }
        }
        if (hasDown(row, col)) {
            int down = positionToNum(row + 1, col);
            if (isOpen(row + 1, col)) {
                uf.union(cur, down);
                uf2.union(cur, down);
            }
        }
    }

    private boolean hasLeft(int row, int col) {
        return !outOfBound(row, col - 1);
    }

    private boolean hasRight(int row, int col) {
        return !outOfBound(row, col + 1);
    }
    private boolean hasUp(int row, int col) {
        return !outOfBound(row - 1, col);
    }
    private boolean hasDown(int row, int col) {
        return !outOfBound(row + 1, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (outOfBound(row, col)) throw new IllegalArgumentException();
        return openOrNot[positionToNum(row, col)] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (outOfBound(row, col)) throw new IllegalArgumentException();
        int num = positionToNum(row, col);
        return uf2.connected(0, num);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(v1, v2);
    }

    private boolean outOfBound(int row, int col) {
        return row < 1 || row > sideLength || col < 1 || col > sideLength;
    }

    private int positionToNum(int row, int col) {
        return (row - 1) * sideLength + col;
    }
    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        for (int i = 1; i < 5; i++) {
            System.out.println("false " + p.hasLeft(i, 1));
        }
        p.open(2, 1);
        System.out.println("true " + p.isOpen(2, 1));
        p.open(3, 3);
        p.open(3, 4);
        System.out.println("false  " + p.percolates());
        p.open(1, 3);
        p.open(4, 3);
        p.open(4, 3);
        System.out.println("false  " + p.percolates());
        p.open(1, 4);
        System.out.println("false  " + p.percolates());
        p.open(2, 3);
        System.out.println("true  " + p.percolates());

        System.out.println("7  " + p.numberOfOpenSites());

    }
}