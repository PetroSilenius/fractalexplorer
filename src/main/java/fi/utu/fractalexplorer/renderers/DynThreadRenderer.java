package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.kernels.MandelbrotFastKernel;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public class DynThreadRenderer extends RenderBase implements MandelbrotFastKernel, MandelbrotDynamicParallelRenderer {
    public DynThreadRenderer(int w, int h, int maxIterations, PixelRenderer p) {
        super(w, h, maxIterations, p);
    }
}