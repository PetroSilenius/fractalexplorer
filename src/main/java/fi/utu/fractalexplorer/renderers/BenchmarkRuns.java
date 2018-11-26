package fi.utu.fractalexplorer.renderers;

/**
 * Represents a set of benchmark runs.
 *
 * This class is final. Modification is not necessary.
 */
public class BenchmarkRuns {
    public int runs = 0;
    public long renderTime = 0;
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
     * @return A summary of the benchmark runs.
     */
    public String toString() {
        return "" + renderer + ", " + runs + " runs, " + renderTime + " ms.";
    }
}