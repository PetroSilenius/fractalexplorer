package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.kernels.MandelbrotFastKernel;
import fi.utu.fractalexplorer.kernels.MandelbrotSlowKernel;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public class RendererFactory {
    public static MandelbrotRenderer createRenderer(RendererType type, int w, int h, int maxIterations, PixelRenderer p) throws Exception {

        class FastRenderer extends RenderBase implements MandelbrotFastKernel, MandelbrotSequentialRenderer {
            public FastRenderer(int w, int h, int maxIterations, PixelRenderer p) {
                super(w, h, maxIterations, p);
            }
        }

        class SlowRenderer extends RenderBase implements MandelbrotSlowKernel, MandelbrotSequentialRenderer {
            public SlowRenderer(int w, int h, int maxIterations, PixelRenderer p) {
                super(w, h, maxIterations, p);
            }
        }

        class StaticThreadRenderer extends RenderBase implements MandelbrotFastKernel, MandelbrotStaticParallelRenderer {
            public StaticThreadRenderer(int w, int h, int maxIterations, PixelRenderer p) {
                super(w, h, maxIterations, p);
            }
        }

        class DynThreadRenderer extends RenderBase implements MandelbrotFastKernel, MandelbrotDynamicParallelRenderer {
            public DynThreadRenderer(int w, int h, int maxIterations, PixelRenderer p) {
                super(w, h, maxIterations, p);
            }
        }

        switch (type) {
            case Slow:
                return new SlowRenderer(w, h, maxIterations, p);
            case Vector:
                return new FastRenderer(w, h, maxIterations, p);
            case StaticTheaded:
                return new StaticThreadRenderer(w, h, maxIterations, p);
            case DynamicTheaded:
                return new DynThreadRenderer(w, h, maxIterations, p);
            case ThreadedWorkQueue:
                throw new Exception("Unimplemented renderer type "+type);
                // TODO: this requires something more..
                // return new DynThreadRenderer(w, h, maxIterations, p);
            default:
                throw new Exception("Unknown renderer type "+type);
        }
    }
}