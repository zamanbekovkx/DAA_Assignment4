package daa;

public class TwoRowEditDistance implements EditDistanceAlgorithm {
    private long counter;

    /**
     * DP design (two rows):
     * State: prev/curr store two adjacent DP rows for prefixes of a and b.
     * Transition: curr[j] uses curr[j-1], prev[j], and prev[j-1].
     * Base case: the first row is 0..m; each new row starts with its index.
     */
    @Override
    public int distance(String a, String b) {
        if (a == null || b == null) throw new IllegalArgumentException("Strings cannot be null");
        counter = 0;
        if (b.length() > a.length()) {
            String tmp = a; a = b; b = tmp;
        }
        int n = a.length();
        int m = b.length();
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];
        for (int j = 0; j <= m; j++) prev[j] = j;

        /*
         * State: prev/curr store two adjacent DP rows for prefixes of a and b.
         * Transition: each curr[j] uses curr[j-1], prev[j], and prev[j-1].
         * Base case: the first row is 0..m; each new row starts with its index.
         */
        for (int i = 1; i <= n; i++) {
            curr[0] = i;
            for (int j = 1; j <= m; j++) {
                counter++;
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    curr[j] = prev[j - 1];
                } else {
                    curr[j] = 1 + Math.min(prev[j], Math.min(curr[j - 1], prev[j - 1]));
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }
        return prev[m];
    }

    @Override
    public void resetCounter() { counter = 0; }

    @Override
    public long getCounter() { return counter; }
}
