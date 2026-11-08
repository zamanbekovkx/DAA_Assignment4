package daa;

public class RecursiveEditDistance implements EditDistanceAlgorithm {
    private long counter;

    /**
     * DP design (recursive form):
     * State: solve(i,j) is the edit distance between a[0..i) and b[0..j).
     * Transition: matching last characters uses solve(i-1,j-1); otherwise 1 + min(delete, insert, replace).
     * Base case: if i=0 return j; if j=0 return i.
     */
    @Override
    public int distance(String a, String b) {
        if (a == null || b == null) throw new IllegalArgumentException("Strings cannot be null");
        counter = 0;
        return solve(a, b, a.length(), b.length());
    }

    /*
     * State: solve(i,j) is the edit distance between a[0..i) and b[0..j).
     * Transition: if last characters match, use solve(i-1,j-1); otherwise take
     * 1 + min(delete, insert, replace). Base case: if i=0 return j; if j=0 return i.
     */
    private int solve(String a, String b, int i, int j) {
        counter++;
        if (i == 0) return j;
        if (j == 0) return i;
        if (a.charAt(i - 1) == b.charAt(j - 1)) {
            return solve(a, b, i - 1, j - 1);
        }
        int delete = solve(a, b, i - 1, j);
        int insert = solve(a, b, i, j - 1);
        int replace = solve(a, b, i - 1, j - 1);
        return 1 + Math.min(delete, Math.min(insert, replace));
    }

    @Override
    public void resetCounter() { counter = 0; }

    @Override
    public long getCounter() { return counter; }
}
