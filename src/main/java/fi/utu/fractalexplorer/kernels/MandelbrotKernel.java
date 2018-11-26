package fi.utu.fractalexplorer.kernels;

/**
 * Interface MandelbrotKernel represents entities that can draw the mandelbrot set.
 *
 * This class is final. Modification is not necessary.
 */
public interface MandelbrotKernel {
    /**
     * @return Size of the vector struct.
     */
    int vectorSize();

    /**
     * Mandelbrot kernel function, return the iteration count for set of points.
     *
     * Calculates the points (x,y), (x+inc, y), (x+2*inc, y), ... up to vectorSize() elements
     * Also performs rotation for each point.
     *
     * @return iteration counts for an array of vectorSize() points
     */
    int[] mandelbrot(double x, double y, double inc, double rot);

    /**
     * Mandelbrot kernel function, return the iteration count for a point.
     *
     * @return Iteration count for the point (c_re, c_im)
     */
    int mandelbrot(double c_re, double c_im);

    /**
     * @return Maximum number of iterations.
     */
    int getMaxIterations();
}