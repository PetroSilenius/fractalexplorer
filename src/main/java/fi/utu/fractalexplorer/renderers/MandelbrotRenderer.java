package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.util.Viewport;
import fi.utu.fractalexplorer.kernels.MandelbrotKernel;

/**
 * You need to consider if this needs to be changed in order to support all rendering modes.
 */
public interface MandelbrotRenderer extends MandelbrotKernel {
    int renderWidth();

    int renderHeight();

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

    // draw the whole set
    void drawSet(Viewport vp);
}
