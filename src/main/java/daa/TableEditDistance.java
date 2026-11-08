package daa;

public class TableEditDistance implements EditDistanceAlgorithm {
    private long counter;

    /**
     * DP design (full table):
     * State: dp[i][j] is the edit distance between a[0..i) and b[0..j).
     * Transition: match uses dp[i-1][j-1]; otherwise 1 + min(delete, insert, replace).
     * Base case: dp[i][0]=i and dp[0][j]=j.
     */
    @Override
    public int distance(String a, String b) {
        if (a == null || b == null) throw new IllegalArgumentException("Strings cannot be null");
        counter = 0;
        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        /*
         * State: dp[i][j] is the edit distance between a[0..i) and b[0..j).
         * Transition: match uses dp[i-1][j-1]; otherwise 1 + min(delete, insert, replace).
         * Base case: dp[i][0]=i and dp[0][j]=j.
         */
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                counter++;
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j],
                            Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[n][m];
    }

    @Override
    public void resetCounter() { counter = 0; }

    @Override
    public long getCounter() { return counter; }
}
