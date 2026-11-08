package daa;

public interface EditDistanceAlgorithm {
    int distance(String a, String b);
    void resetCounter();
    long getCounter();
}
