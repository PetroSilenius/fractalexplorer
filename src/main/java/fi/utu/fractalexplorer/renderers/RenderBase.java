package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.kernels.MandelbrotFastKernel;

/**
 * Provides basic functionality for connecting to a pixel renderer and storing basic
 * image parameters. A vector storage is provided for those that need it.
 *
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
abstract class RenderBase implements MandelbrotRenderer {
    private final int width, height, maxIterations;
    private final PixelRenderer pixelRenderer;

    public RenderBase(int width, int height, int maxIterations, PixelRenderer pixelRenderer) {
        this.width = width;
        this.height = height;
        this.maxIterations = maxIterations;
        this.pixelRenderer = pixelRenderer;
    }

    @Override
    public int renderWidth() {
        return width;
    }

    @Override
    public int renderHeight() {
        return height;
    }

    @Override
    public int getMaxIterations() {
        return maxIterations;
    }

    @Override
    public PixelRenderer pixelRenderer() {
        return pixelRenderer;
    }

    private final MandelbrotFastKernel.VectorizedMandelbrot storage = new MandelbrotFastKernel.VectorizedMandelbrot();

    public MandelbrotFastKernel.VectorizedMandelbrot vectorStorage() {
        return storage;
    }
}