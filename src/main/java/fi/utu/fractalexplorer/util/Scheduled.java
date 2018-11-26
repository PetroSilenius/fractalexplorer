package fi.utu.fractalexplorer.util;

/**
 * Interface for classes that are scheduled to do something.
 * @see fi.utu.fractalexplorer.Ticker
 *
 * This class is final. Modification is not necessary.
 */
public interface Scheduled {
    /**
     * @return Time to wait between periodic ticks (in milliseconds) .
     */
    int tickDuration();

    /**
     * Run this at program startup.
     */
    void initialize();

    /**
     * Run periodically (update period set via int tickDuration()).
     */
    void tick();
}
