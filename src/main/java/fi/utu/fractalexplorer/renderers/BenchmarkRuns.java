package fi.utu.fractalexplorer.renderers;

/**
 * Represents a set of benchmark runs.
 *
 * This class is final. Modification is not necessary.
 */
public class BenchmarkRuns {
    private int runs = 0;
    private long renderTime = 0;
    public final String renderer;

    public BenchmarkRuns(RendererType type) {
        this.renderer = type.description;
    }

    /**
     * Log a single rendering run and accumulate the render time counter.
     *
     * @param renderTime rendering time in milliseconds
     */
    public void add(long renderTime) {
        runs++;
        this.renderTime += renderTime;
    }

    /**
     * @return Accumulated total render time.
     */
    public long renderTime() {
        return renderTime;
    }

    /**
     * @return A summary of the benchmark runs.
     */
    public String toString() {
        return "" + renderer + ", " + runs + " runs, " + renderTime + " ms.";
    }
}