package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.util.Viewport;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public class DummyPixelRenderer implements PixelRenderer {
    private final int w, h;
    public final int[] data;

    public DummyPixelRenderer(int w, int h, int vectorSize) {
        this.w = w;
        this.h = h;
        data = new int[w * h + vectorSize];
    }

    @Override
    public void setPixel(int idx, int i) {
        data[idx] = i;
    }

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