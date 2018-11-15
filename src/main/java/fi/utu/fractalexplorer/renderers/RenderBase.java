package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.kernels.MandelbrotFastKernel;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
abstract class RenderBase implements MandelbrotRenderer {
    final int w, h, maxIterations;
    final PixelRenderer pixelRenderer;

    public RenderBase(int w, int h, int maxIterations, PixelRenderer pixelRenderer) {
        this.w = w;
        this.h = h;
        this.maxIterations = maxIterations;
        this.pixelRenderer = pixelRenderer;
    }

    @Override
    public int renderWidth() {
        return w;
    }

    @Override
    public int renderHeight() {
        return h;
    }

    @Override
    public int getMaxIterations() {
        return maxIterations;
    }

    public PixelRenderer pixelRenderer() {
        return pixelRenderer;
    }

    private final MandelbrotFastKernel.VectorizedMandelbrot storage = new MandelbrotFastKernel.VectorizedMandelbrot();

    public MandelbrotFastKernel.VectorizedMandelbrot vectorStorage() {
        return storage;
    }
}