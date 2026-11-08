package daa;

import java.util.ArrayList;
import java.util.List;

public final class StringSearch {
    private StringSearch() {}

    public static final class Counter {
        public long textComparisons;
        public long lpsComparisons;
        public long total() { return textComparisons + lpsComparisons; }
    }

    public static List<Integer> naive(String text, String pattern, Counter counter) {
        validate(text, pattern);
        if (pattern.length() > text.length()) return new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m) {
                counter.textComparisons++;
                if (text.charAt(i + j) != pattern.charAt(j)) break;
                j++;
            }
            if (j == m) result.add(i);
        }
        return result;
    }

    public static List<Integer> kmp(String text, String pattern, Counter counter) {
        validate(text, pattern);
        if (pattern.length() > text.length()) return new ArrayList<>();
        int[] lps = lps(pattern, counter);
        List<Integer> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            counter.textComparisons++;
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == pattern.length()) {
                    result.add(i - j);
                    j = lps[j - 1];
                }
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return result;
    }

    public static int[] lps(String p) {
        return lps(p, new Counter());
    }

    private static int[] lps(String p, Counter counter) {
        if (p == null) throw new IllegalArgumentException("Pattern cannot be null");
        int[] lps = new int[p.length()];
        int len = 0;
        int i = 1;
        while (i < p.length()) {
            counter.lpsComparisons++;
            if (p.charAt(i) == p.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }

    private static void validate(String text, String pattern) {
        if (text == null || pattern == null) throw new IllegalArgumentException("Text and pattern cannot be null");
        if (pattern.isEmpty()) throw new IllegalArgumentException("Pattern cannot be empty");
    }
}
