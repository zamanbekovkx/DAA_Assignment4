package daa;

import java.util.List;

public class ReferenceCheck {
    public static void main(String[] args) {
        EditDistanceAlgorithm[] ed = {new RecursiveEditDistance(), new TableEditDistance(), new TwoRowEditDistance()};
        String[][] pairs = {{"kitten", "sitting"}, {"flaw", "lawn"}, {"intention", "execution"}, {"", "abc"}, {"abc", ""}};
        int[] expected = {3, 2, 5, 3, 3};
        for (EditDistanceAlgorithm alg : ed) {
            for (int i = 0; i < pairs.length; i++) {
                int got = alg.distance(pairs[i][0], pairs[i][1]);
                if (got != expected[i]) throw new AssertionError(alg.getClass() + " failed reference test");
            }
        }
        if (!java.util.Arrays.equals(StringSearch.lps("AAACAAAA"), new int[]{0,1,2,0,1,2,3,3})) throw new AssertionError();
        if (!java.util.Arrays.equals(StringSearch.lps("ABABCABAB"), new int[]{0,0,1,2,0,1,2,3,4})) throw new AssertionError();
        StringSearch.Counter c1 = new StringSearch.Counter();
        StringSearch.Counter c2 = new StringSearch.Counter();
        if (!StringSearch.kmp("ABABDABACDABABCABAB", "ABABCABAB", c1).equals(List.of(10))) throw new AssertionError();
        if (!StringSearch.kmp("AAAAA", "AA", c2).equals(List.of(0,1,2,3))) throw new AssertionError();
        if (!StringSearch.kmp("ABABABA", "ABA", new StringSearch.Counter()).equals(List.of(0,2,4))) throw new AssertionError();
        System.out.println("Reference checks passed.");
    }
}
