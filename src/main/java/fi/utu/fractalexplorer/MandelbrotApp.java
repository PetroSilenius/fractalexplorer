package fi.utu.fractalexplorer;

import fi.utu.fractalexplorer.javafx.AsyncFXMandelbrotCanvas;
import fi.utu.fractalexplorer.renderers.DummyPixelRenderer;
import fi.utu.fractalexplorer.renderers.RendererType;
import fi.utu.fractalexplorer.javafx.FXConsole;
import fi.utu.fractalexplorer.javafx.FXNodeList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class MandelbrotApp initializes JavaFX and the GUI.
 *
 * This class is final. Modification might not be necessary.
 */
public class MandelbrotApp extends Application {
    private final float viewRatio = 2f / 3f;

    // configure this if this is too slow on your computer (smaller = faster)
    private final int initialIterations = 50;

    private final AsyncFXMandelbrotCanvas canvas = new AsyncFXMandelbrotCanvas(initialIterations, RendererType.Vector);
    private final Label statusLabel = new Label("Executing benchmarks..");
    private boolean started = false;

    boolean benchmarkStarted() {
        boolean t = started;
        started = true;
        return t;
    }

    private Node buildBar(List<Node> nodes) {
        FlowPane p = new FlowPane();
        p.getChildren().addAll(nodes);
        for (Node n : nodes)
            n.setFocusTraversable(false);
        p.setHgap(8);
        p.setVgap(8);
        return p;
    }

    private Button createButton(String text, EventHandler<ActionEvent> handler) {
        Button b = new Button(text);
        b.setOnAction(handler);
        return b;
    }

    private FXNodeList topBarContent() {
        return new FXNodeList(statusLabel);
    }

    private FXNodeList bottomBarContent() {
        Stream<Node> types = Arrays.stream(RendererType.values()).map(n -> createButton(n.description, e -> canvas.setRendererType(n)));
        return new FXNodeList(types).cat(
                createButton("Detail++", e -> canvas.addDetail()),
                createButton("Detail--", e -> canvas.removeDetail()),
                createButton("Exit", e -> System.exit(0))
        );
    }

    void benchmark() {
        for(RendererType t: RendererType.values()) {
            DummyPixelRenderer d = new DummyPixelRenderer(2000, 2000, 8);
            String result = String.valueOf(d.benchmark(t, 100, 10));

            Platform.runLater(() -> {
                if (!benchmarkStarted()) statusLabel.setText("Benchmark: ");
                statusLabel.setText(statusLabel.getText() + " "+result); });
        }
    }

    /**
     * The JavaFX startup routine.
     * <p>
     * Good enough for us, thus final.
     */
    @Override
    public final void start(Stage primaryStage) {
        System.out.println("javafx.runtime.version: " + System.getProperties().get("javafx.runtime.version"));
        System.out.println("java version: " + System.getProperty("java.version"));

        new Thread(this::benchmark).start();

        primaryStage.setTitle("Fractal explorer");
        Rectangle2D screen = Screen.getPrimary().getBounds();
        BorderPane root = new BorderPane();
        root.setTop(buildBar(topBarContent()));
        root.setBottom(buildBar(bottomBarContent()));
        Pane main = new Pane();
        main.prefWidthProperty().bind(root.widthProperty().multiply(viewRatio));
        canvas.peer.widthProperty().addListener(o -> canvas.redraw());
        canvas.peer.heightProperty().addListener(o -> canvas.redraw());
        canvas.peer.widthProperty().bind(main.widthProperty());
        canvas.peer.heightProperty().bind(main.heightProperty());
        main.getChildren().add(canvas.peer);
        root.setLeft(main);
        TextArea consoleArea = new FXConsole();
        consoleArea.prefWidthProperty().bind(root.widthProperty().multiply(1 - viewRatio));
        consoleArea.setEditable(false);
        consoleArea.setFocusTraversable(false);
        root.setRight(consoleArea);
        primaryStage.setScene(new Scene(root, screen.getWidth() * 2 / 3, screen.getHeight() * 2 / 3));
        primaryStage.show();
        Ticker handler = new Ticker(canvas);
        handler.initialize();
        int duration = handler.tickDuration();
        // return if a repeating loop is not needed
        if (!(duration > 0)) return;

        String durString = "" + ((duration > 1000) ? duration / 1000 + " " : duration + " milli");
        System.out.println("Initializing a scheduled timer every " + durString + "seconds.");

        // repeating application logic loop
        Timeline tl = new Timeline(
                new KeyFrame(new Duration(duration), e -> {
                    try {
                        handler.tick();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.exit(1);
                    }
                })
        );
        tl.setAutoReverse(true);
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
    }
}