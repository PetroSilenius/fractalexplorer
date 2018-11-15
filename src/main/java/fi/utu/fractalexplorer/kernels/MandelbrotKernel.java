package fi.utu.fractalexplorer.kernels;

/**
 * Interface MandelbrotKernel represents entities that can draw the mandelbrot set.
 *
 * This class is final. Modification is not necessary.
 */
public interface MandelbrotKernel {
    // size of the vector struct
    int vectorSize();

    // mandelbrot kernel function, return the iteration count for set of points (size = vectorSize())
    int[] mandelbrot(double x, double y, double inc, double rot);

    // mandelbrot kernel function, return the iteration count for a point
    int mandelbrot(double c_re, double c_im);

    // maximum number of iterations
    int getMaxIterations();
}