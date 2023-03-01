package shakespearesearch.utils.eval;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

public class RunTimer {
    private String algorithm;
    private long startTime;
    private long endTime;
    private int numMatches;

    private static final String ALGO_KEY = "algo";
    private static final String NUM_THREADS_KEY = "i";
    private static final String TOTAL_TIME_KEY = "totalTime";
    private static final String AVG_TIME_KEY = "avgTime";
    private static final String USED_MEMORY_KEY = "usedMemory";

    public enum MemoryUnit {
        BYTES(1L),
        KILOBYTES(1024L),
        MEGABYTES(1024L * 1024L),
        GIGABYTES(1024L * 1024L * 1024L);

        private final long multiplier;

        MemoryUnit(long multiplier) {
            this.multiplier = multiplier;
        }

        public long toBytes(long value) {
            return value / multiplier;
        }
    }

    public enum TimeDisplay {
        NANOS(1L),
        MICROS(1000L),
        MILLIS(1000L * 1000L),
        SECS(1000L * 1000L * 1000L);

        private final long multiplier;

        TimeDisplay(long multiplier) {
            this.multiplier = multiplier;
        }

        public long toMillis(long value) {
            return value / multiplier;
        }
    }

    private MemoryUnit memoryDisplay = MemoryUnit.MEGABYTES;
    private TimeDisplay timeDisplay = TimeDisplay.MILLIS;

    public RunTimer(String algorithm) {
        this.algorithm = algorithm;
    }

    public void start() {
        this.startTime = System.nanoTime();
    }

    public Map<String, Object> stop(int numMatches, int numThreads, boolean printLine) {
        this.endTime = System.nanoTime();
        long elapsedTime = getElapsedTime();
        long usedMemory = getUsedMemory();
        long avgTimePerMatch = getAvgTimePerMatch();

        Map<String, Object> runResult = new HashMap<>();
        runResult.put(ALGO_KEY, algorithm);
        runResult.put(NUM_THREADS_KEY, numThreads);
        runResult.put(TOTAL_TIME_KEY, timeDisplay.toMillis(elapsedTime));
        runResult.put(AVG_TIME_KEY, timeDisplay.toMillis(avgTimePerMatch));
        runResult.put(USED_MEMORY_KEY, memoryDisplay.toBytes(usedMemory));

        if (printLine) {
            System.out.printf("i: %d, Total Time: %d ms, Avg Time: %d ms, Memory: %d %s\n",
                    numThreads, timeDisplay.toMillis(elapsedTime), timeDisplay.toMillis(avgTimePerMatch),
                    memoryDisplay.toBytes(usedMemory), memoryDisplay.name().toLowerCase());
        }

        return runResult;
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

    public void setMemoryDisplay(MemoryUnit unit) {
        this.memoryDisplay = unit;
    }

    public void setTimeDisplay(TimeDisplay unit) {
        this.timeDisplay = unit;
    }
}
