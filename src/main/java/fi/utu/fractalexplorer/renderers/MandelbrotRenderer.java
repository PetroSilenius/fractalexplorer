package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.util.Viewport;
import fi.utu.fractalexplorer.kernels.MandelbrotKernel;

/**
 * Interface for renderers that can draw the Mandelbrot set
 * (as a whole, single pixels, or regions).
 *  - for single pixels, use int mandelbrot(double, double)
 *  - for regions, use void drawTile(int, int, int, int, Viewport)
 *  - for the whole set, use void drawSet(Viewport)
 *
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public interface MandelbrotRenderer extends MandelbrotKernel {
    /**
     * @return Width of the whole rendered image.
     */
    int renderWidth();

    /**
     * @return Height of the whole rendered image.
     */
    int renderHeight();

    /**
     * Implemented in subclasses. Allows rendering to different
     * surfaces, e.g. arrays, image buffers, JavaFX image objects etc.
     *
     * @return A renderer object used for storing individual pixel values.
     */
    PixelRenderer pixelRenderer();

    /**
     * Draw a region of the Mandelbrot set. The pixel coordinates
     * (inclusive) of the region are (tx, ty) - (tx+tw-1, ty+th-1).
     *
     * Due to the vector processing, tw % vectorSize() == 0 must be
     * always true!
     *
     * Params:
     *  (tx, ty) - upper left coordinate (inclusive)
     *  (tw, th) - tile width & height
     *  vp       - viewport settings
     *
     */
    default void drawTile(int tx, int ty, int tw, int th, Viewport vp) {
        final double w = renderWidth(), h = renderHeight();
        final int ww = renderWidth();
        final int v = vectorSize();

        // tile bottom-right coordinates (exclusive)
        final int mx = tx + tw;
        final int my = ty + th;

        // viewport parameters
        final double zoom = vp.zoom / Math.min(w, h) * 2;
        final double zoom2 = 2 * zoom;
        final double s = Math.sin(vp.rot);
        final double c = Math.cos(vp.rot);
        final double vpc = vp.dx - c;
        final double vps = vp.dy + s;

        double x, sx = - w * zoom;
        double y = - h * zoom;
        for (int i=0;i<ty;i++) y  += zoom2; // emulate float imprecision
        for (int i=0;i<tx;i++) sx += zoom2; // emulate float imprecision

        int lineIdx = ty * ww;

        for (int yy = ty; yy < my; yy++) {
            x = sx;

            for (int xx = tx; xx < mx; xx += v) {
                if (v > 1) {

                    int[] iterValues = mandelbrot(vpc + x, vps + y, zoom2, vp.rot);

                    for (int i = 0; i < v; i++)
                        pixelRenderer().setPixel(lineIdx + xx + i, iterValues[i]);

                    x += zoom2 * v;
                } else {
                    final double c_re = vpc + x;
                    final double c_im = vps + y;

                    // some affine transforms..
                    int iterValue = mandelbrot(c_re * c - c_im * s, c_re * s + c_im * c);
                    //int iterValue = mandelbrot(c_re,  c_im );

                    pixelRenderer().setPixel(lineIdx + xx, iterValue);
                    x += zoom2;
                }
            }
            lineIdx += ww;
            y += zoom2;
        }
    }

    /**
     * Draws the whole Mandelbrot set.
     *
     * Subclasses can implement different rendering strategies,
     * e.g. sequential, parallel, tiled etc. The work can be
     * delegated to drawTile() or the pixel/vector kernel functions in
     * fi.utu.fractalexplorer.kernels.MandelbrotKernel
     *
     * @param vp Viewport parameters. See fi.utu.fractalexplorer.MandelbrotCanvas
     *           and fi.utu.fractalexplorer.util.Viewport for examples.
     */
    void drawSet(Viewport vp);
}
