package fi.utu.fractalexplorer.javafx;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Basic GUI console.
 *
 * This class is final. Modification is not necessary.
 */
public class FXConsole extends TextArea {
    private final OutputStream stream = new OutputStream() {
        @Override
        public void write(int b) {
            buffer.write(b);

            Platform.runLater(() -> {
                if (b == '\n') {
                    FXConsole.this.appendText(new String(buffer.toByteArray(), StandardCharsets.UTF_8));
                    buffer.reset();
                }
            });

        }
    };
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public FXConsole() {
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                originalOut.write(b);
                stream.write(b);
            }
        }));
    }
}
