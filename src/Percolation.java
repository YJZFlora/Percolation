import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *  only import：
 *  StdIn, StdOut, StdRandom, StdStats, WeightedQuickUnionUF
 *  java.lang.
 */

public class Percolation {
    private final int sideLength;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;
    private final int v1;
    private final int v2;
    private boolean[] openOrNot;
    private int count;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        sideLength = n;
        v1 = 0;
        v2 = n * n + 1;
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 1);
        openOrNot = new boolean[n * n + 1];
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
        if (outOfBound(row, col)) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }

        int cur = positionToNum(row, col);
        openOrNot[cur] = true;
        count++;

        if (col > 1 && isOpen(row, col - 1)) {
            int left = positionToNum(row, col - 1);
                uf.union(cur, left);
                uf2.union(cur, left);
        }
        if (col < sideLength && isOpen(row, col + 1)) {
            int right = positionToNum(row, col + 1);
                uf.union(cur, right);
                uf2.union(cur, right);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            int up = positionToNum(row - 1, col);
                uf.union(cur, up);
                uf2.union(cur, up);
        }
        if (row < sideLength && isOpen(row + 1, col)) {
            int down = positionToNum(row + 1, col);
                uf.union(cur, down);
                uf2.union(cur, down);
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (outOfBound(row, col)) {
            throw new IllegalArgumentException();
        }
        return openOrNot[positionToNum(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (outOfBound(row, col)) {
            throw new IllegalArgumentException();
        }
        int num = positionToNum(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        return uf2.connected(0, num);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        if (sideLength == 1) {
            return isOpen(1, 1);
        }
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

    }
}
