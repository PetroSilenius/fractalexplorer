package fi.utu.fractalexplorer.util;

/**
 * Interface for classes that are scheduled to do something.
 * @see fi.utu.fractalexplorer.Ticker
 *
 * This class is final. Modification is not necessary.
 */
public interface Scheduled {
    int tickDuration();
    void initialize();
    void tick();
}
