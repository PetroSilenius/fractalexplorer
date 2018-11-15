package fi.utu.fractalexplorer.renderers;

import java.io.Serializable;

/**
 * Represents the different rendering modes.
 *
 * This class is final. Modification is not necessary.
 */
public enum RendererType implements Serializable {
    Slow("Scalar"),
    Vector("Vector"),
    StaticTheaded("Threaded (static sched)"),
    DynamicTheaded("Theaded (dyn. sched)"),
    ThreadedWorkQueue("Threaded + work queues");

    RendererType(String desc) {
        description = desc;
    }

    public final String description;
}