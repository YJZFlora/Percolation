# Percolation
percolation algorithm exercise

0. OverAll Solution
* use WeightedQuickUnionUF uf to remember relationships between sites.
* use 2 virtual sites to mark top and bottom line.
  If just using true sites in the top line and bottom line,
  isFull(), percolate() checking if a site is connect to top or bottom will call O(n) times of uf.isConnected().
  Now only take O(1) time.

1. Cover conner case:

* when n == 1, Virtual sites v1 and v2 are connected at first. So, method percolates() should cover this case by adding
if (sideLength == 1) {
    return isOpen(1, 1);
}

* method isfull() should not only check isConnected(v1, cur), as the first line is always connect with v1.
  A full site should So should check isOpen() first.

2. PMD

* The private instance (or static) variable 'sideLength' can be made 'final';
  because it is initialized only in the declaration or constructor.

* The constant '1.96' appears more than once.
  Define a constant variable (such as 'CONFIDENCE_95') to hold the constant '1.96'.
  '''private final static double CONFIDENCE_95 = 1.96;

3. Backwash Problem:

* problem: when the grid is percolate, checking uf.isConnected(v1, cur) to determine isFull() is not always valid.
  Because v2 is connected to v1, when the site is only connected to v2, it will be judged to be full.
* solution: use another WeightedQuickUnionUF uf2 with only the top virtual site v1.
  when checking isFull(), check uf2.isConnected(v1, cur) instead.
