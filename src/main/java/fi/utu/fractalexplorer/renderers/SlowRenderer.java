package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.kernels.MandelbrotSlowKernel;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public class SlowRenderer extends RenderBase implements MandelbrotSlowKernel, MandelbrotSequentialRenderer {
    public SlowRenderer(int w, int h, int maxIterations, PixelRenderer p) {
        super(w, h, maxIterations, p);
    }
}