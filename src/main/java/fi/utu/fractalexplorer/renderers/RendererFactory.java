package fi.utu.fractalexplorer.renderers;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public class RendererFactory {
    public static MandelbrotRenderer createRenderer(RendererType type, int w, int h, int maxIterations, PixelRenderer p) throws Exception {

        switch (type) {
            case Slow:
                return new SlowRenderer(w, h, maxIterations, p);
            case Vector:
                return new FastRenderer(w, h, maxIterations, p);
            case StaticThreaded:
                return new StaticThreadRenderer(w, h, maxIterations, p);
            case ThreadedWorkQueue:
            case DynamicThreaded:
                return new DynThreadRenderer(w, h, maxIterations, p);
            default:
                throw new Exception("Unknown renderer type "+type);
        }
    }
}