package daa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ExperimentRunner {
    private static final int RUNS = 5;
    private static final long SEED = 2516L;

    public static void main(String[] args) throws Exception {
        Path out = Path.of("results/results.csv");
        Files.createDirectories(out.getParent());
        List<String> rows = new ArrayList<>();
        rows.add("family,algorithm,input,n,time_ms,counter,extra_counter,run");

        runEditDistance(rows);
        runSearch(rows);
        Files.write(out, rows, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Wrote " + out.toAbsolutePath());
    }

    private static void runEditDistance(List<String> rows) {
        InputGenerator gen = new InputGenerator(SEED);
        List<EditDistanceAlgorithm> algorithms = List.of(
                new RecursiveEditDistance(), new TableEditDistance(), new TwoRowEditDistance());
        int[] sizes = {3, 4, 6, 8, 12};
        for (String input : List.of("random", "worst")) {
            for (int n : sizes) {
                String a = input.equals("random") ? gen.randomString(n) : gen.repeated('a', n);
                String b = input.equals("random") ? gen.randomString(n) : gen.repeated('b', n);
                for (EditDistanceAlgorithm alg : algorithms) {
                    for (int run = 1; run <= RUNS; run++) {
                        alg.resetCounter();
                        long start = System.nanoTime();
                        int value = alg.distance(a, b);
                        double ms = (System.nanoTime() - start) / 1_000_000.0;
                        if (value < 0) throw new AssertionError();
                        rows.add(String.format(Locale.US, "edit_distance,%s,%s,%d,%.6f,%d,0,%d",
                                alg.getClass().getSimpleName(), input, n, ms, alg.getCounter(), run));
                    }
                }
            }
        }
    }

    private static void runSearch(List<String> rows) {
        InputGenerator gen = new InputGenerator(SEED + 1);
        int[] sizes = {1000, 4000, 8000, 16000};
        for (String input : List.of("random", "worst")) {
            for (int n : sizes) {
                int m = Math.max(4, n / 4);
                String text;
                String pattern;
                if (input.equals("random")) {
                    text = gen.randomString(n);
                    pattern = gen.randomPattern(m);
                } else {
                    text = gen.worstSearchText(n);
                    pattern = gen.worstSearchPattern(m);
                }
                for (String algorithm : List.of("Naive", "KMP")) {
                    for (int run = 1; run <= RUNS; run++) {
                        StringSearch.Counter c = new StringSearch.Counter();
                        long start = System.nanoTime();
                        List<Integer> result = algorithm.equals("Naive")
                                ? StringSearch.naive(text, pattern, c)
                                : StringSearch.kmp(text, pattern, c);
                        double ms = (System.nanoTime() - start) / 1_000_000.0;
                        if (result == null) throw new AssertionError();
                        rows.add(String.format(Locale.US, "string_search,%s,%s,%d,%.6f,%d,%d,%d",
                                algorithm, input, n, ms, c.total(), c.lpsComparisons, run));
                    }
                }
            }
        }
    }
}
