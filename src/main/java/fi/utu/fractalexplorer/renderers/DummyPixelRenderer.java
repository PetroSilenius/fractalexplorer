package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.util.Viewport;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public class DummyPixelRenderer implements PixelRenderer {
    private final int w, h;

    // can be used for comparing the rendering result
    public final int[] data;

    /**
     * Initialize benchmark parameters.
     *
     * @param w Image width.
     * @param h Image height.
     * @param vectorSize Vector size (used to add some padding to avoid index out of bounds exceptions).
     */
    public DummyPixelRenderer(int w, int h, int vectorSize) {
        this.w = w;
        this.h = h;
        data = new int[w * h + vectorSize];
    }

    /**
     * Stores the calculated pixel value to the integer array of pixels.
     */
    @Override
    public void setPixel(int pixelIdx, int colorValue) {
        data[pixelIdx] = colorValue;
    }

    /**
     * Benchmark a fractal renderer of type 'type'. Run the rendering with maximum number of
     * 'maxIterations' iterations. The rendering is performed runCount times so stabilize the
     * results.
     *
     * @param type Renderer type, @see fi.utu.fractalexplorer.renderers.RendererType
     * @param maxIterations maximum number of iterations
     * @param runCount The rendering is performed runCount times
     * @return Benchmark result data
     */
    public BenchmarkRuns benchmark(RendererType type, int maxIterations, int runCount) {
        try {
            MandelbrotRenderer r = RendererFactory.createRenderer(type, w, h, maxIterations, this);
            BenchmarkRuns runs = new BenchmarkRuns(type);

            // warm up Java JIT
            if (runCount>1)
                for (int i = 0; i < 6; i++) r.drawSet(new Viewport());

            for (int i = 0; i < runCount; i++) {
                long start = System.currentTimeMillis();
                r.drawSet(new Viewport());
                long stop = System.currentTimeMillis();
                runs.add(stop - start);
            }

            return runs;
        } catch (Exception e) {
            return new BenchmarkRuns(type);
        }
    }
}