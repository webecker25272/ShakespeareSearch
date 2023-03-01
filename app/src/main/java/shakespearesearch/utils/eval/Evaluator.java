package shakespearesearch.utils.eval;

import java.lang.management.ManagementFactory;

public class Evaluator {
    private String algorithm;
    private int numThreads;
    private long startTime;
    private long endTime;
    private int numMatches;

    public Evaluator(String algorithm) {
        this.algorithm = algorithm;
    }

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void stop(int numMatches, int numThreads) {
        this.endTime = System.nanoTime();
        this.numMatches = numMatches;
        this.numThreads = numThreads;
    }

    public void evaluate() {
        long elapsedTime = getElapsedTime();
        long usedMemory = getUsedMemory();
        long avgTimePerMatch = getAvgTimePerMatch();
        System.out.printf(
                "Algo: %-30s Threads: %-10d Matches: %-10d Total Time: %-20s Avg Time: %-20s Memory: %-20s\n",
                algorithm, numThreads, numMatches, formatNanos(elapsedTime), formatNanos(avgTimePerMatch),
                formatBytes(usedMemory));
    }

    private long getElapsedTime() {
        return this.endTime - this.startTime;
    }

    private long getUsedMemory() {
        long used = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
        used += ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed();
        return used;
    }

    private long getAvgTimePerMatch() {
        return this.numMatches > 0 ? getElapsedTime() / this.numMatches : 0;
    }

    private String formatNanos(long nanos) {
        if (nanos < 1000) {
            return nanos + " ns";
        } else if (nanos < 1000000) {
            return nanos / 1000 + " μs";
        } else if (nanos < 1000000000) {
            return nanos / 1000000 + " ms";
        } else {
            return nanos / 1000000000 + " s";
        }
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1048576) {
            return bytes / 1024 + " KB";
        } else if (bytes < 1073741824) {
            return bytes / 1048576 + " MB";
        } else {
            return bytes / 1073741824 + " GB";
        }
    }
}
