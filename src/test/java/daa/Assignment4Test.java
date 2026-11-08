package daa;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Assignment4Test {
    private EditDistanceAlgorithm[] algorithms() {
        return new EditDistanceAlgorithm[]{new RecursiveEditDistance(), new TableEditDistance(), new TwoRowEditDistance()};
    }

    @Test
    void editDistanceReferenceValues() {
        for (EditDistanceAlgorithm alg : algorithms()) {
            assertEquals(3, alg.distance("kitten", "sitting"));
            assertEquals(2, alg.distance("flaw", "lawn"));
            assertEquals(5, alg.distance("intention", "execution"));
            assertEquals(3, alg.distance("", "abc"));
            assertEquals(3, alg.distance("abc", ""));
            assertEquals(0, alg.distance("abc", "abc"));
        }
    }

    @Test
    void editDistanceRandomAgreement() {
        Random r = new Random(2516);
        for (int t = 0; t < 100; t++) {
            String a = randomShort(r);
            String b = randomShort(r);
            int expected = algorithms()[1].distance(a, b);
            for (EditDistanceAlgorithm alg : algorithms()) assertEquals(expected, alg.distance(a, b));
        }
    }

    private String randomShort(Random r) {
        int n = r.nextInt(7);
        String alphabet = "abc";
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) s.append(alphabet.charAt(r.nextInt(alphabet.length())));
        return s.toString();
    }

    @Test
    void lpsReferenceArrays() {
        assertArrayEquals(new int[]{0,1,2,0,1,2,3,3}, StringSearch.lps("AAACAAAA"));
        assertArrayEquals(new int[]{0,0,1,2,0,1,2,3,4}, StringSearch.lps("ABABCABAB"));
    }

    @Test
    void searchReferenceValuesAndOverlaps() {
        assertEquals(List.of(10), StringSearch.naive("ABABDABACDABABCABAB", "ABABCABAB", new StringSearch.Counter()));
        assertEquals(List.of(10), StringSearch.kmp("ABABDABACDABABCABAB", "ABABCABAB", new StringSearch.Counter()));
        assertEquals(List.of(0,1,2,3), StringSearch.naive("AAAAA", "AA", new StringSearch.Counter()));
        assertEquals(List.of(0,1,2,3), StringSearch.kmp("AAAAA", "AA", new StringSearch.Counter()));
        assertEquals(List.of(0,2,4), StringSearch.naive("ABABABA", "ABA", new StringSearch.Counter()));
        assertEquals(List.of(0,2,4), StringSearch.kmp("ABABABA", "ABA", new StringSearch.Counter()));
    }

    @Test
    void searchRandomAgreement() {
        Random r = new Random(2517);
        for (int t = 0; t < 100; t++) {
            String text = random(r, 1 + r.nextInt(30));
            String pattern = random(r, 1 + r.nextInt(8));
            List<Integer> n = StringSearch.naive(text, pattern, new StringSearch.Counter());
            List<Integer> k = StringSearch.kmp(text, pattern, new StringSearch.Counter());
            assertEquals(n, k);
        }
    }

    private String random(Random r, int n) {
        StringBuilder s = new StringBuilder();
        String alphabet = "abca";
        for (int i = 0; i < n; i++) s.append(alphabet.charAt(r.nextInt(alphabet.length())));
        return s.toString();
    }

    @Test
    void edgeCases() {
        assertThrows(IllegalArgumentException.class, () -> StringSearch.naive("abc", "", new StringSearch.Counter()));
        assertThrows(IllegalArgumentException.class, () -> StringSearch.kmp("abc", "", new StringSearch.Counter()));
        assertEquals(List.of(), StringSearch.naive("ab", "abcd", new StringSearch.Counter()));
        assertEquals(List.of(), StringSearch.kmp("ab", "abcd", new StringSearch.Counter()));
    }
}
