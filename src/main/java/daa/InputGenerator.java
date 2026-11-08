package daa;

import java.util.Random;

public class InputGenerator {
    private final Random random;

    public InputGenerator(long seed) {
        random = new Random(seed);
    }

    public String randomString(int n) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        return sb.toString();
    }

    public String repeated(char c, int n) {
        return String.valueOf(c).repeat(n);
    }

    public String randomPattern(int n) {
        return randomString(n);
    }

    public String worstSearchText(int n) {
        if (n == 0) return "";
        return "a".repeat(Math.max(0, n - 1)) + "b";
    }

    public String worstSearchPattern(int m) {
        if (m == 0) return "";
        return "a".repeat(Math.max(0, m - 1)) + "b";
    }
}
