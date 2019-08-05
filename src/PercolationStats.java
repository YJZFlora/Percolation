import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 0 || trials < 0) {
            throw new IllegalArgumentException();
        }

        int base = n * n;
        double[] counts = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            boolean pass = false;
            while (!pass) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
                if (p.percolates()) {
                    pass = true;
                }
            }
            counts[i] = (double) p.numberOfOpenSites() / (double) base;
        }

        mean = StdStats.mean(counts);
        stddev = StdStats.stddev(counts);
        confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);
        confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);

    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    /**
      *takes two command-line arguments n and T,
     * performs T independent computational experiments (discussed above) on an n-by-n grid,
     * and prints the sample mean, sample standard deviation,
     * and the 95% confidence interval for the percolation threshold.
     */

    public static void main(String[] args) {

        if (args[0] == null || args[1] == null) {
            System.out.println("Please give 2 command line arguments");
            throw new IllegalArgumentException();
        }
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");


    }
}
